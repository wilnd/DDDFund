package com.finpoints.bss.common.event;

public interface ExternalEventConverter {

    Object convert(ExternalEvent event);

    class DefaultExternalEventConverter implements ExternalEventConverter {

        @Override
        public Object convert(ExternalEvent event) {
            return event;
        }
    }
}
