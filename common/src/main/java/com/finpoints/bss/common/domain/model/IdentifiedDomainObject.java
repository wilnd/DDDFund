package com.finpoints.bss.common.domain.model;

import java.io.Serializable;

public interface IdentifiedDomainObject extends Serializable {

    String delegateId();
}
