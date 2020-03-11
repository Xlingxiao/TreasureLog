package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 19:57
 * @Description: expend表
 */
@Data
@Entity(name = "expend")
public class Expend {

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
    private Date insertTime;


}
