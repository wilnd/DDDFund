package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class JpaEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(64) comment '应用ID'")
    private String appId;

    @CreatedDate
    @Column(columnDefinition = "datetime(6) comment '创建时间'")
    private Instant createdTime;

    @LastModifiedDate
    @Column(columnDefinition = "datetime(6) comment '修改时间'")
    private Instant updatedTime;

    @Column(columnDefinition = "boolean default false comment '逻辑删除标识'")
    private Boolean deleted = false;

    public void copyFrom(Entity entity) {
        this.id = entity.delegateId() == null ? null : Long.valueOf(entity.delegateId());
        this.appId = entity.getAppId();
        this.createdTime = entity.getCreatedTime();
        this.updatedTime = entity.getUpdatedTime();
    }

    public void copyTo(Entity entity) {
        entity.setAppId(this.getId() == null ? null : String.valueOf(this.getId()), this.getAppId());
        entity.setTime(this.getCreatedTime(), this.getUpdatedTime());
    }
}
