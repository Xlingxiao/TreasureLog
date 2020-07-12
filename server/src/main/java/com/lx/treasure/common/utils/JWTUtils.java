package com.lx.treasure.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.ContentText;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Lx
 * @Date: 2020/6/25 15:54
 * @Description: jwt工具
 */
@Component
@Slf4j
public class JWTUtils {

    private static Key key;

    private static SecretKey jwtKey;


    // Spring 扫描到@Value注解时会自动调用下面的方法完成static属性的注入
    @Value("${auth.jwt.key}")
    private void setJwtKey(String key) {
        jwtKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    /**
     * 获取token数据
     *
     * @param token token
     */
    public static synchronized Map<String, Object> getTokenParams(String token) throws CommonException {
        String errMsg = null;
        if (StringUtils.isEmpty(token)) {
            errMsg = ContentText.TOKEN_EMPTY_ERROR;
        } else {
            try {
                Map<String, Object> tokenParams = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
                log.info("token params: {}", tokenParams);
                return tokenParams;
            } catch (ExpiredJwtException jwtException) {
                errMsg = ContentText.TOKEN_INVALID_ERROR;
            } catch (Exception e) {
                e.printStackTrace();
                errMsg = ContentText.TOKEN_ERROR;
            }
        }
        if (!StringUtils.isEmpty(errMsg)) {
            throw new CommonException(ContentText.TOKEN_ERROR_CODE, errMsg);
        }
        return null;
    }

    /**
     * 将参数转为token
     *
     * @param params 参数
     * @param role   用户角色
     * @return token
     */
    public static synchronized String paramsToToken(String role, Map<String, Object> params) {
        String token = Jwts.builder()
                .setSubject(role)
                .setClaims(params)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(jwtKey).compact();
        return token;
    }
}
