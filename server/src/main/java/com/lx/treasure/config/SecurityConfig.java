//package com.lx.treasure.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests().antMatchers("/login").permitAll();
//        http.formLogin();
//
////        http.authorizeRequests().antMatchers("/login").permitAll();
////        http.authorizeRequests().requestMatchers((RequestMatcher) request -> "ni6+BAAAAwQCXx0L0HBg2T2GkmuUTW4vP95EwUk1p".equals(request.getHeader("token"))).permitAll();
//
//    }
//
//}
