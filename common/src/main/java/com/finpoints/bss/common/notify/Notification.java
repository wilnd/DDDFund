package com.finpoints.bss.common.notify;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class Notification extends ValueObject {

    private final String title;
    private final String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
