package com.lx.treasure.module.login.repository;

import com.lx.treasure.module.login.bo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: Lx
 * @Date: 2020/5/3 19:24
 * @Description:
 */

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Query(nativeQuery = true,value = "select * from user where user_account = ?1")
    UserInfo findUserByAccount(String userAccount);
}
