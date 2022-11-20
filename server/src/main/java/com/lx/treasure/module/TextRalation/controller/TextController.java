package com.lx.treasure.module.TextRalation.controller;

import com.lx.treasure.module.TextRalation.bo.TextLogVo;
import com.lx.treasure.module.TextRalation.service.TextServiceImpl;
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
