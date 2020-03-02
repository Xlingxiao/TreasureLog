package com.lx.treasure.bean.common;

import lombok.Data;

import java.util.Date;

/**
 * 用于将数据库id解析为此对象
 * @author LX
 */
@Data
public class DataId {
    // 城市id
    long cityNum;
    // 机房id
    long machineRoomNum;
    // 机架id
    long machineFrameNum;
    // 机器id
    long machineNum;
    // 插入时间
    Date timeMillsNum;
    // 插入时间内的排序
    long orderNum;
}
