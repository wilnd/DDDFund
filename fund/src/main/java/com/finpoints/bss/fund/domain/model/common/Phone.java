package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

@Getter
public class Phone extends ValueObject {

    private final String area;
    private final String number;

    public Phone(String area, String number) {
        Validate.notBlank(area, "Phone area must not be null.");
        Validate.notBlank(number, "Phone number must not be null.");

        this.area = area;
        this.number = number;
    }

    /**
     * 获取受保护的手机号码
     * CN: 15217808825 -> 152****8825
     * HK: 22224444    -> 22****44
     * US: 2223334444  -> 222****444
     *
     * @return 受保护的手机号码
     */
    public String protectedNumber() {
        if (StringUtils.isEmpty(number)) {
            return number;
        }
        if (number.length() == 8) {
            return StringUtils.overlay(number, "****", 2, 6);
        } else if (number.length() > 8) {
            return StringUtils.overlay(number, "****", 3, 7);
        }
        return number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Phone phone = (Phone) object;
        return Objects.equals(area, phone.area) && Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, number);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "area='" + area + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
