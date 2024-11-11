package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Fullname extends ValueObject {

    private final String firstname;     // 名
    private final String lastname;      // 姓
    private final String fullname;      // 全名

    public Fullname(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = firstname + " " + lastname;
    }

    public Fullname(String firstname, String lastname, String fullname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Fullname fullname1 = (Fullname) object;
        return Objects.equals(firstname, fullname1.firstname) && Objects.equals(lastname, fullname1.lastname) && Objects.equals(fullname, fullname1.fullname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, fullname);
    }

    @Override
    public String toString() {
        return "Fullname{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
