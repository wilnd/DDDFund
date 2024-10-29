package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class JpaVersionalEntityBase extends JpaEntityBase {

    @Version
    @Column(columnDefinition = "int comment '版本号'")
    private Integer version;

    @Override
    public void copyFrom(Entity entity) {
        super.copyFrom(entity);
        this.version = entity.getVersion();
    }

    @Override
    public void copyTo(Entity entity) {
        super.copyTo(entity);
        entity.setVersion(this.version);
    }
}
