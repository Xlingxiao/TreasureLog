package com.lx.treasure.mapper;

import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.mapper.TreasureClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreasureClassMapper {

    /**
     * 获取汇总的状态信息
     * @param userAccount 用户信息
     * @return 汇总的资产信息
     */
    List<TreasureClassInfo> getTreasureClassInfo(long userAccount);

    /**
     * 更新info 表
     * @param info
     * @return
     */
    int updateInfo(Info info);
}
