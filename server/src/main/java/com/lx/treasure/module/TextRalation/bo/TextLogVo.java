package com.lx.treasure.module.TextRalation.bo;

import com.lx.treasure.bean.ioBean.BaseInvo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextLogVo extends BaseInvo {

    private String text;

}
