package com.lx.treasure.module.common.service;

import com.lx.treasure.module.common.bo.AesInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理用户的AESKey信息
 */
@Service
public class AESKeyService {

    private Map<String, AesInfo> AESKeyMap = new HashMap<>(16);

    /**
     * 保存aec信息
     */
    public void saveAesInfo(String user, AesInfo aesInfo) {
        AESKeyMap.put(user, aesInfo);
    }

    public AesInfo getAesInfo(String user) {
        return AESKeyMap.get(user);
    }
}
