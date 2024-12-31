package com.lx.treasure.module.treasure.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 常数
 */
public class TreasureConstant {

    /** 投资相关资产 */
    public static List<String> INVEST_CHANNEL_LIST = new ArrayList<String>(){{
        add("理财");
        add("基金");
        add("证券");
        add("定存");
        add("帮你");
    }};
}
