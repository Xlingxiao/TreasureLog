package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 22:25
 * @Description: 投资表
 */
@Entity(name = "fund_info")
@Data
public class Fund {

    @Id
    private long id;

    // 个人账号
    private long userAccount;

    // 投资
    private double invest;

    // 当前总额
    private double gross;

    // 记录时间
    private Date insert_time;

}
