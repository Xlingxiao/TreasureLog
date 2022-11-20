package com.lx.treasure.module.treasure.service;

import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.ExpendVo;
import com.lx.treasure.module.treasure.mapper.Expend;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.mapper.SpendInfo;
import com.lx.treasure.common.utils.DateUtils;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.module.treasure.repository.ExpendRepository;
import com.lx.treasure.module.treasure.repository.InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
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

    private static DecimalFormat df = new DecimalFormat("#.00");
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
        if(userAccount == 0) return null;
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
        Expend expend = inVoToExpend(expendVo);
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

    /**
     * 获取支出收入信息
     * @param baseInvo 基本输入
     * @return 支出收入信息
     */
    public SpendInfo getSpendInfo(BaseInvo baseInvo) {
        SpendInfo spendInfo = new SpendInfo();
        loadMonthSpeedInfo(baseInvo, spendInfo);
        loadYearSpeedInfo(baseInvo, spendInfo);
        return spendInfo;
    }

    /**
     * 加载 月消费金额 日均消费金额
     *
     * @param baseInvo 前端输入
     * @param spendInfo 需要加载的返回对象
     */
    private void loadMonthSpeedInfo(BaseInvo baseInvo, SpendInfo spendInfo) {
        List<Info> infos = infoRepository.findUserLatest(baseInvo.getUserAccount(), 2);
        if (infos == null || infos.size() < 1) return;
        Info lastInfo = infos.get(0);
        double lastMonthPay = lastInfo.getPay();
        double lastMonthSpend = lastInfo.getExpenditure();

        if (infos.size() == 2) {
            long logSpanTime = lastInfo.getInsertTime().getTime() - infos.get(1).getInsertTime().getTime();
            int logSpanDay = (int) (logSpanTime / (60 * 60 * 24 * 1000));
            double dailySpend = lastMonthPay / logSpanDay;
            dailySpend = Double.parseDouble(df.format(dailySpend));
            spendInfo.setDailySpend(dailySpend);
        }
        spendInfo.setMonthSpend(lastMonthSpend);
        spendInfo.setMonthlyIncome(lastMonthPay);
    }

    /**
     * 获取 年消费金额 年总收入
     * @param baseInvo 前端输入
     * @param spendInfo 需要加载的返回对象
     */
    private void loadYearSpeedInfo(BaseInvo baseInvo,SpendInfo spendInfo){
        try {
            Date currentYearDate = DateUtils.getCurrentYearFirstDate();
            Date currentDate = new Date();
            List<Info> infos = infoRepository.findInfoByUserAccount(baseInvo.getUserAccount(), currentYearDate, currentDate);
            if (infos != null) {
                double yearSpend = 0;
                double annualIncome = 0;
                for (Info info : infos) {
                    yearSpend += info.getExpenditure();
                    annualIncome += info.getPay();
                }
                spendInfo.setYearSpend(yearSpend);
                spendInfo.setAnnualIncome(annualIncome);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
