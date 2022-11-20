package com.lx.treasure.bean.ioBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/2/25 12:44
 * @Description: 渠道的信息
 */

@Component
@Data
public class ChannelsInfo {
    public ChannelsInfo() {
    }

    public ChannelsInfo(String name, double value, List<ChannelsInfo> children) {
        this.name = name;
        this.value = value;
        this.children = children;
    }

    // 渠道名
    private String name;
    // 渠道值
    private double value;
    // 子渠道
    private List<ChannelsInfo> children;
}
