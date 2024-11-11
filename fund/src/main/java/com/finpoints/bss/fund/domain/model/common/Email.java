package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Email extends ValueObject {

    private String address;

    public Email(String anAddress) {
        super();

        this.setAddress(anAddress);
    }

    public String address() {
        return this.address;
    }

    private void setAddress(String anAddress) {
        Validate.notEmpty(anAddress, "The email address is required.");
        Validate.isTrue(
                anAddress.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"),
                "Email address format is invalid.");

        this.address = anAddress;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Email email = (Email) object;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }
}
