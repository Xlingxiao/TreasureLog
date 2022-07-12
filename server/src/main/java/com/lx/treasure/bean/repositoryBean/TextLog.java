package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "text_log")
public class TextLog {

    public TextLog() {
    }

    public TextLog(int id, String content, String userAccount, Date createTime, Date updateTime) {
        this.id = id;
        this.content = content;
        this.userAccount = userAccount;
        this.insertTime = createTime;
        this.updateTime = updateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private String userAccount;

    private Date insertTime;

    private Date updateTime;
}
