package com.lx.treasure.module.treasure.mapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 对应info表
 * Entity 告诉jpa这是实体类
 * Table 指定表名 不指定默认使用类名
 */
@Data
@Entity
@Table(name = "info")
public class Info {

    // id，用于标志主键
    @Id
    private long id;
    // 用户id
    private long userAccount;
    // 薪水
    private double pay;
    // 花费
    private double expenditure;
    // 消费介绍
    private String info;
    // 插入时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date insertTime;
    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

}


