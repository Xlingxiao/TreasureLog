package com.lx.treasure.common.utils;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Token 黑名单服务
 * 用于在用户登出时将 token 标记为无效
 * 使用内存存储，过期的 token 会自动清理
 */
@Component
public class TokenBlacklistService {

    // key: token, value: 加入黑名单的时间戳
    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    // token 有效期 1 小时（与 JWTUtils 中一致）
    private static final long TOKEN_EXPIRATION_MS = 3600000;

    /**
     * 将 token 加入黑名单
     */
    public void add(String token) {
        cleanup();
        blacklist.put(token, System.currentTimeMillis());
    }

    /**
     * 检查 token 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }

    /**
     * 清理已过期的 token（超过 1 小时的 token 即使不清理也已失效）
     */
    private void cleanup() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> it = blacklist.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            if (now - entry.getValue() > TOKEN_EXPIRATION_MS) {
                it.remove();
            }
        }
    }
}
