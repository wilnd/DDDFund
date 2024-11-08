package com.finpoints.bss.common.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.finpoints.bss.common.domain.model.AbstractId;
import com.finpoints.bss.common.domain.model.ValueObject;
import org.springframework.modulith.events.core.EventSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

@Component
public class ComponentPathSerializer implements EventSerializer {

    private final ObjectMapper objectMapper;

    public ComponentPathSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object serialize(Object event) {
        try {
            if (event instanceof JsonNode) {
                return objectMapper.writeValueAsString(event);
            }
            ObjectNode root = serializeObject(objectMapper.createObjectNode(), "", event);
            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Object serialized, Class<T> type) {
        if (!(serialized instanceof String)) {
            throw new IllegalArgumentException("Serialized object must be a string");
        }
        try {
            if (JsonNode.class.isAssignableFrom(type)) {
                return (T) objectMapper.readTree((String) serialized);
            }
            JsonNode root = objectMapper.readTree((String) serialized);
            return (T) deserializeByConstructor(root, "", type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object deserializeByConstructor(JsonNode root, String prefix, Class<?> type) throws Exception {
        Constructor<?> constructor = null;
        for (Constructor<?> declaredConstructor : type.getDeclaredConstructors()) {
            Parameter[] parameters = declaredConstructor.getParameters();
            if (parameters.length == 0) {
                try {
                    return deserializeByField(root, prefix, type);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (parameters.length != root.size()) {
                continue;
            }
            constructor = declaredConstructor;
            break;
        }
        if (constructor == null) {
            throw new RuntimeException("No valid constructor found for " + type);
        }

        Object[] args = new Object[constructor.getParameterCount()];
        for (int i = 0; i < args.length; i++) {
            Parameter parameter = constructor.getParameters()[i];
            try {
                args[i] = deserializeObject(root, prefix + parameter.getName(), parameter.getType());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return constructor.newInstance(args);
    }

    private Object deserializeByField(JsonNode root, String prefix, Class<?> type) throws Exception {
        Object instance = type.getConstructor().newInstance();
        for (Field declaredField : type.getDeclaredFields()) {
            if (Modifier.isTransient(declaredField.getModifiers())) {
                continue;
            }
            declaredField.setAccessible(true);
            declaredField.set(instance, deserializeObject(root,
                    prefix + declaredField.getName(), declaredField.getType()));
        }
        return instance;
    }

    private Object deserializeObject(JsonNode root, String name, Class<?> type) throws Exception {
        JsonNode fieldNode = root.get(name);
        if (fieldNode == null) {
            return null;
        }

        if (String.class.isAssignableFrom(type)) {
            return fieldNode.asText();
        } else if (Boolean.class.isAssignableFrom(type)) {
            return fieldNode.asBoolean();
        } else if (Enum.class.isAssignableFrom(type)) {
            return Enum.valueOf((Class<Enum>) type, fieldNode.asText());
        } else if (AbstractId.class.isAssignableFrom(type)) {
            Constructor<?> constructor = type.getConstructor(String.class);
            return constructor.newInstance(fieldNode.asText());
        } else if (ValueObject.class.isAssignableFrom(type)) {
            return deserializeByConstructor(root, name + "_", type);
        }
        return objectMapper.readValue(fieldNode.textValue(), type);
    }

    private ObjectNode serializeObject(ObjectNode root, String prefix, Object object) throws Exception {
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(declaredField.getModifiers())) {
                continue;
            }
            declaredField.setAccessible(true);

            Object fieldData;
            try {
                fieldData = declaredField.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (fieldData instanceof String str) {
                root.put(prefix + declaredField.getName(), str);
            } else if (fieldData instanceof Boolean bool) {
                root.put(prefix + declaredField.getName(), bool);
            } else if (fieldData instanceof Enum<?> enumValue) {
                root.put(prefix + declaredField.getName(), enumValue.name());
            } else if (fieldData instanceof AbstractId abstractId) {
                root.put(prefix + declaredField.getName(), abstractId.rawId());
            } else if (fieldData instanceof ValueObject) {
                serializeObject(root, prefix + declaredField.getName() + "_", fieldData);
            } else {
                root.put(prefix + declaredField.getName(), objectMapper.writeValueAsString(fieldData));
            }
        }
        return root;
    }
}
