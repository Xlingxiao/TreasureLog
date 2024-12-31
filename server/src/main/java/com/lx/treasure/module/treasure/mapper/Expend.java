package com.lx.treasure.module.treasure.mapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 19:57
 * @Description: expend表
 */

@Data
@Builder
@Entity(name = "expend")
public class Expend {

    public Expend() {
    }

    public Expend(long id, long userAccount, String info, double amount, int essential, String detail, Date insertTime) {
        this.id = id;
        this.userAccount = userAccount;
        this.info = info;
        this.amount = amount;
        this.essential = essential;
        this.detail = detail;
        this.insertTime = insertTime;
    }

    @Id
    private long id;

    // 个人账号
    private long userAccount;

    // 购买信息
    private String info;

    // 花费的钱
    private double amount;

    // 是否必要 0 必要 1 不必要 别的字段留着
    private int essential;

    // 备注
    private String detail;

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date insertTime;


}
