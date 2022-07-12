package com.lx.treasure.service.text;

import com.alibaba.fastjson.JSONArray;
import com.lx.treasure.bean.ioBean.TextLogVo;
import com.lx.treasure.bean.repositoryBean.TextLog;
import com.lx.treasure.repository.TextLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TextServiceImpl {

    @Resource
    TextLogRepository textLogRepository;

    public String getText(TextLogVo textLogVo) {
        List<TextLog> textList = textLogRepository.getTextByUser(textLogVo);
        return JSONArray.toJSONString(textList);
    }

    public String addOneText(TextLogVo textLogVo) {
        TextLog textLog = new TextLog();
        textLog.setContent(textLogVo.getText());
        textLog.setUserAccount(String.valueOf(textLogVo.getUserAccount()));
        textLog.setInsertTime(new Date());
        textLog.setUpdateTime(new Date());
        TextLog save = textLogRepository.save(textLog);
        return save.toString();
    }

}
