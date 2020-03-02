package com.lx.treasure.bean.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取Id相关配置项，使用ConfigurationProperties自动注入配置项
 */

@Component
@ConfigurationProperties(prefix = "lx.machine-id")
@Data
public class DataIdProp {

    public DataIdProp() {
        System.out.println("loading prop");
    }

    // 城市id
    long cityNum;
    // 机房id
    long machineRoomNum;
    // 机架id
    long machineFrameNum;
    // 机器id
    long machineNum;
    // 城市id所占2进制位数
    long cityNumPlaces;
    // 机房id所占2进制位数
    long machineRoomNumPlaces;
    // 机架id所占2进制位数
    long machineFrameNumPlaces;
    // 机器id所占2进制位数
    long machineNumPlaces;
    // 时间戳所占2进制位数
    long timeMillsPlaces;
    // 同一时间戳内的多个id所占2进制位数
    long concurrencePlaces;
    // 初始时间：yyyy-MM-dd格式
    String fixedTime;
}
