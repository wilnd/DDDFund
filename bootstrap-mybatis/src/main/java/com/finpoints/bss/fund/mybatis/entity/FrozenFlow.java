package com.finpoints.bss.fund.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@TableName("api_frozen_flow")
public class FrozenFlow implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String appid;

    private String uuid;

    private String walletId;

    private String currency;

    private String orderSerial;

    private String frozenType;

    private BigDecimal frozenBalance;

    @TableField(value = "`status`")
    private Integer status;

    private String unfrozenType;

    private Timestamp unfrozenTime;

    private String remark;

    private Timestamp createTime;

    private Timestamp updateTime;

    private int isDeleted;
}
