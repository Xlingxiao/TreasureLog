package com.lx.treasure.service;

import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.ExpendVo;
import com.lx.treasure.bean.repositoryBean.Expend;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.repository.ExpendRepository;
import com.lx.treasure.repository.InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 20:17
 * @Description: 支出相关服务提供
 */

@Service
@Slf4j
public class ExpendService {

    @Resource
    private ExpendRepository expendRepository;

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private SuccessResponse successResponse;

    @Autowired
    InfoRepository infoRepository;



    /**
     * 获取消费数据
     *
     * @param userAccount 前端输入的
     * @param startDate 获取指定时间之后的数据
     * @param endDate 获取指定时间之前的数据
     * @return 用户个人的消费数据
     */
    public List<Expend> getExpendInfo(long userAccount, Date startDate, Date endDate) {
        if (StringUtils.isEmpty(userAccount)) {
            return null;
        }
        return expendRepository.findByUserAccount(userAccount, startDate, endDate);
    }



    /**
     * 保存多条消费记录
     *
     * @param expendVos 前端输入的多条消费记录
     * @return 成功标志
     */
    public SuccessResponse addExpend(List<ExpendVo> expendVos) {
        List<Expend> expendList = inVoToExpend(expendVos);
        expendRepository.saveAll(expendList);
        return successResponse;
    }

    /**
     * 保存一条消费记录
     *
     * @param expendVo 前端输入的一条消费记录
     * @return 成功标志
     */
    public SuccessResponse addExpend(ExpendVo expendVo) {
        com.lx.treasure.bean.repositoryBean.Expend expend = inVoToExpend(expendVo);
        expendRepository.save(expend);
        return successResponse;
    }

    /**
     * 将前端输入的列表转为数据库bean的列表
     *
     * @param expendVoList 前端输入的expendInVo List
     * @return 数据库Bean ExpendInVo List
     */
    private List<Expend> inVoToExpend(List<ExpendVo> expendVoList) {
        List<Expend> expendList = new LinkedList<>();
        for (ExpendVo expendVo : expendVoList) {
            if (expendVo.getAmount() > 0){
                expendList.add(inVoToExpend(expendVo));
            }
        }
        return expendList;
    }

    /**
     * 将输入的记录转为数据库表对应的bean对象
     *
     * @param expendVo 前端输入的一条消费记录
     * @return 数据库Expend 表对应的Bean
     */
    private Expend inVoToExpend(ExpendVo expendVo) {
        Expend expend = new Expend();
        BeanUtils.copyProperties(expendVo, expend);
        expend.setInsertTime(new Date());
        expend.setId(idUtils.generateId());
        return expend;
    }
}
