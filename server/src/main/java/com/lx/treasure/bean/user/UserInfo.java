package com.lx.treasure.bean.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: Lx
 * @Date: 2020/5/3 19:18
 * @Description: 用户对象
 */

@Builder
@Data
@Entity(name = "user")
@Table(name = "user")
public class UserInfo {

    public UserInfo() {
    }

    public UserInfo(int id, String userAccount, String nickName, int userType, String password) {
        this.id = id;
        this.userAccount = userAccount;
        this.nickName = nickName;
        this.userType = userType;
        this.password = password;
    }

    @Id
    private int id;
    // 用户账号
    private String userAccount;
    // 昵称
    private String nickName;
    // 用户类型
    private int userType;
    // 密码
    private String password;


}
