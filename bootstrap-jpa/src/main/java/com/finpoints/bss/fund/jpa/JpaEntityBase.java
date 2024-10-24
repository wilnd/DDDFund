package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class JpaEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(64) comment '应用ID'")
    private String appId;

    @Version
    @Column(columnDefinition = "int comment '版本号'")
    private Integer version;

    @CreatedDate
    @Column(columnDefinition = "datetime(6) comment '创建时间'")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(columnDefinition = "datetime(6) comment '修改时间'")
    private LocalDateTime updatedTime;

    @Column(columnDefinition = "boolean default false comment '逻辑删除标识'")
    private Boolean deleted = false;

    public void copyFrom(Entity entity) {
        this.id = entity.delegateId();
        this.appId = entity.getAppId();
        this.version = entity.getVersion();
        this.createdTime = entity.getCreatedTime();
        this.updatedTime = entity.getUpdatedTime();
    }

    public void copyTo(Entity entity) {
        entity.setAppIdAndVersion(this.getId(), this.getAppId(), this.getVersion());
        entity.setTime(this.getCreatedTime(), this.getUpdatedTime());
    }
}
