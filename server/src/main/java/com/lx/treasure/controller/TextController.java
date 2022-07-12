package com.lx.treasure.controller;

import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.TextLogVo;
import com.lx.treasure.service.text.TextServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/text")
public class TextController {

    @Autowired
    private TextServiceImpl textService;

    @PostMapping("/getText")
    public String getText(@RequestBody TextLogVo inVo) {
        return textService.getText(inVo);
    }

    @PostMapping("/addOneText")
    public String addOneText(@RequestBody TextLogVo inVo) {
        return textService.addOneText(inVo);
    }
}
