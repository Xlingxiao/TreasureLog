package com.lx.treasure.bean.repositoryBean;

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
    private Date insertTime;
    // 更新时间
    private Date updateTime;

}


