package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ResidentID extends ValueObject {

    private final ResidentIDType type;
    private final String number;

    public ResidentID(ResidentIDType type, String number) {
        this.type = type;
        this.number = number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResidentID that = (ResidentID) object;
        return type == that.type && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }

    @Override
    public String toString() {
        return "ResidentID{" +
                "type=" + type +
                ", number='" + number + '\'' +
                '}';
    }
}
