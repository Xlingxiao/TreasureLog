package com.lx.treasure.bean.repositoryBean;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
