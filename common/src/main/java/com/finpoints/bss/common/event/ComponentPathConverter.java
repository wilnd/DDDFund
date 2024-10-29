package com.finpoints.bss.common.event;

import com.finpoints.bss.common.domain.model.AbstractId;
import com.finpoints.bss.common.domain.model.ValueObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

public class ComponentPathConverter implements ExternalEventConverter {

    @Override
    public Object convert(ExternalEvent event) {
        return convertObject(new LinkedHashMap<>(), "", event);
    }

    private LinkedHashMap<String, Object> convertObject(LinkedHashMap<String, Object> fieldMap,
                                                        String prefix, Object object) {
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

            if (fieldData instanceof AbstractId abstractId) {
                fieldMap.put(prefix + declaredField.getName(), abstractId.rawId());
            } else if (fieldData instanceof ValueObject) {
                convertObject(fieldMap, prefix + declaredField.getName() + "_", fieldData);
            } else {
                fieldMap.put(prefix + declaredField.getName(), fieldData);
            }
        }
        return fieldMap;
    }
}
