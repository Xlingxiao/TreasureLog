package com.lx.treasure.module.creditcard.repository;

import com.lx.treasure.module.creditcard.bo.CreditCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 信用卡信息表 repository
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardInfo, Long> {


    @Query(value = "select * from credit_info a where a.user_account = ?1 " +
            "and if(IFNULL(?2,'') != '', a.trade_date > ?2 , 1=1) " +
            "and if(IFNULL(?3,'') !='', a.trade_date < ?3 ,1=1)" +
            "ORDER BY a.trade_date DESC",nativeQuery = true)
    List<CreditCardInfo> queryCreditCardInfo(long userAccount, Date startDate, Date endDate);

}
