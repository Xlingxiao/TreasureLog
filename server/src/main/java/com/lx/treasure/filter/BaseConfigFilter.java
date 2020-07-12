package com.lx.treasure.filter;

import org.springframework.context.annotation.Configuration;


/**
 * @Auther: Lx
 * @Date: 2020/6/25 13:19
 * @Description: 配置Filter的启用
 */

@Configuration
public class BaseConfigFilter {

    /**
     * 如果在过滤器中使用了如下两个注解就不要在此处进行注册，下面两个注解会自动进行注册过滤器，
     * 注意，使用了：@WebFilter注解后不要使用 @Component 注解会导致过滤器重复注册
     * @Order(1)
     * @WebFilter(urlPatterns = "/*",filterName = "loginFilter")
     */

   /* @Autowired
    LxLoginFilter lxLoginFilter;*/

   /* // 登录过滤器
    @Bean
    public LoginFilter loginFilterRegistration() {
        return new LoginFilter();
    }*/
}
