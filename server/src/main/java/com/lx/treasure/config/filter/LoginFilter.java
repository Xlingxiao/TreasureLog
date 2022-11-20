package com.lx.treasure.config.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.ContentText;
import com.lx.treasure.common.utils.JWTUtils;
import com.lx.treasure.common.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Lx
 * @Date: 2020/6/25 13:08
 * @Description: 登录过滤器
 *
 * 1. 获取用户的请求url如果是不需要校验token的直接通过
 * 2. 获取请求头中的token，并解析
 * 3. 将token中解析出来的用户信息存入请求参数中
 * 4. token解析出错的情况返回报错信息
 */

@Slf4j
@Order(1)
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
public class LoginFilter implements Filter {


    // 不需要检查token的url
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Value("#{'${auth.dontCheckToken}'.replaceAll(' ','').split(',')}")
    private List<String> dontCheckURI = new ArrayList<>();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LxServletRequestWrapper lxRequest = new LxServletRequestWrapper((HttpServletRequest) servletRequest);
        HttpServletRequest request = lxRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestStr = StreamUtils.streamToString(request.getInputStream(), request.getContentLength());
        String requestURI = request.getRequestURI();
        // 在请求执行完之后浏览器会再发一次请求.ico的请求
        // 防止浏览器请求两次服务器导致的报错
        if (!requestURI.matches(".+.ico$")) {
            log.info("请求URI:{}", requestURI);
            log.info("请求方法:{}", request.getMethod());
            log.info("请求参数：{}",requestStr);
            if (!dontCheckURI.contains(requestURI)) {
                System.out.println("需要校验uri");
                Map<String, Object> tokenParams = checkToken(request, response);
                if (tokenParams == null) {
                    // 校验不通过直接返回
                    log.error("权限校验不通过，token:{}",request.getHeader("Authorization"));
                    return;
                }
                // 装载用户 userAccount
                String paramStr = filterAuth(requestStr, tokenParams);
                // 装载出错 直接返回
                if (StringUtils.isEmpty(paramStr)) {
                    response.sendError(ContentText.INPUT_ERROR_CODE, ContentText.INPUT_ERROR);
                    return;
                }
                lxRequest.setRequestBody(paramStr);
            }
        }
        filterChain.doFilter(request,response);

    }

    /**
     * token校验
     * @param request 请求
     * @param response 响应
     * @throws IOException 处理token报错
     */
    private Map<String,Object> checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String,Object> tokenParams = JWTUtils.getTokenParams(request.getHeader("Authorization"));
            if (tokenParams == null) {
                throw new CommonException(ContentText.TOKEN_ERROR_CODE, ContentText.TOKEN_ERROR);
            }
            log.info("token校验成功！");
            return tokenParams;
        } catch (CommonException commonException) {
            response.sendError(commonException.getResultCode(), commonException.getMessage());
            log.debug(commonException.getMessage());
        }
        return null;
    }

    /**
     * 装载用户识别id
     *
     * @param requestParamsStr 用户请求参数
     * @param tokenParams   用户登录后存token的参数
     * @return 装载好的用户请求参数
     */
    private String filterAuth(String requestParamsStr, Map<String, Object> tokenParams) {
        String tokenAccount = (String) tokenParams.get("userAccount");
        try {
            JSONObject requestParams = JSONObject.parseObject(requestParamsStr);
            requestParams.put("userAccount", tokenAccount);
            return requestParams.toJSONString();
        } catch (JSONException e) {
            try {
                JSONArray requestArray = JSONArray.parseArray(requestParamsStr);
                for (int i = 0; i < requestArray.size(); i++) {
                    requestArray.getJSONObject(i).put("userAccount", tokenAccount);
                }
                return requestArray.toJSONString();
            } catch (Exception ex) {
                log.error("解析用户请求参数出错：{}", requestParamsStr);
            }
        }
        return null;
    }


}
