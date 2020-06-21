package com.lx.treasure.bean.ioBean;

import com.lx.treasure.bean.repositoryBean.Expend;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/2 22:55
 * @Description:
 */

@Data
public class CompleteLog {

    // 个人账号
    private long userAccount;

    // 薪水
    private double pay;

    // 主要消费信息
//    private String info;
    private List<ExpendVo> expendList;

    // 财富分布
    private List<ChannelInVo> channel;

}
