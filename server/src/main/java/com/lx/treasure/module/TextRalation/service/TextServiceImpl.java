package com.lx.treasure.module.TextRalation.service;

import com.alibaba.fastjson.JSONArray;
import com.lx.treasure.common.utils.AESUtils;
import com.lx.treasure.module.TextRalation.bo.TextLogVo;
import com.lx.treasure.module.TextRalation.mapper.TextLog;
import com.lx.treasure.module.TextRalation.repository.TextLogRepository;
import com.lx.treasure.module.common.bo.AesInfo;
import com.lx.treasure.module.common.service.AESKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TextServiceImpl {

    @Resource
    TextLogRepository textLogRepository;
    @Autowired
    AESKeyService aesKeyService;

    public String getText(TextLogVo textLogVo) {
        List<TextLog> textList = textLogRepository.getTextByUser(textLogVo);
        return JSONArray.toJSONString(textList);
    }

    public String addOneText(TextLogVo textLogVo) {
        String userAccount = String.valueOf(textLogVo.getUserAccount());
        String content = textLogVo.getText();
        AesInfo aesInfo = aesKeyService.getAesInfo(userAccount);
        String decryptContent = null;
        try {
            decryptContent = AESUtils.decrypt(content, true, aesInfo.getKey(), aesInfo.getIv());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解密失败，", e);
        }
        log.info("解密后:{}", decryptContent);
        TextLog textLog = new TextLog();
        textLog.setContent(decryptContent);
        textLog.setUserAccount(userAccount);
        textLog.setInsertTime(new Date());
        textLog.setUpdateTime(new Date());
        TextLog save = textLogRepository.save(textLog);
        return save.toString();
    }

}
