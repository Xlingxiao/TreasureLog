package com.lx.treasure.module.treasure.mapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 22:25
 * @Description: 投资表
 */
@Entity(name = "invest")
@Data
public class Invest {

    @Id
    private long id;

    // 个人账号
    private long userAccount;

    // 投资渠道
    private String channel;

    // 当前投资总额
    private double invest;

    // 当前渠道总资产= 投资额 + 利润
    private double gross;

    // 记录时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date insertTime;

}
