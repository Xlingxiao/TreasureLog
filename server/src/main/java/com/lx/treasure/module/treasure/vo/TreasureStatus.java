package com.lx.treasure.module.treasure.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lx.treasure.bean.ioBean.ChannelsInfo;
import lombok.Data;

import java.util.Date;

/**
 * 每个月的财富状态
 */
@Data
public class TreasureStatus {

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /** 收入 */
    private double pay;

    /** 支出 */
    private double expenditure;

    /** 被动收入 */
    private double passiveIncome;

    /** 各个渠道财富状态 */
    ChannelsInfo channelStatus;

}
