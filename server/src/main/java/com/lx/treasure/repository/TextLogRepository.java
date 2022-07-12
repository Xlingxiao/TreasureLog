package com.lx.treasure.repository;

import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.repositoryBean.TextLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextLogRepository extends JpaRepository<TextLog, Long> {

    @Query(value = "SELECT * FROM text_log table1 " +
            "where table1.user_account = :#{#invo.userAccount} ", nativeQuery = true)
    List<TextLog> getTextByUser(BaseInvo invo);
}
