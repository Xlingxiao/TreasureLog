package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 对应渠道表
 */

@Data
@Entity
public class Channel {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // 外键info的主键
    private long infoId;
    // 个人账号
    private long userAccount;
    // 一级渠道
    private String channel1;
    // 二级渠道
    private String channel2;
    // 渠道对应的值
    private double money;
    // 插入时间
    private Date insertTime;
}
