package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 对应info表
 */
@Data
@Entity
public class Info {

    // id
    @Id
    private long id;
    // 薪水
    private double pay;
    // 花费
    private double expenditure;
    // 消费介绍
    private String info;
    // 插入时间
    private Date insert_time;
    // 更新时间
    private Date update_time;

}


