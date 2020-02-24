package com.lx.treasure.bean.common;

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

    long cityNum;
    long machineRoomNum;
    long machineFrameNum;
    long machineNum;
    long cityNumPlaces;
    long machineRoomNumPlaces;
    long machineFrameNumPlaces;
    long machineNumPlaces;
    long timeMillsPlaces;
    long concurrencePlaces;
    String fixedTime;
}
