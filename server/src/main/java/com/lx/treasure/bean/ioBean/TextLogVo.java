package com.lx.treasure.bean.ioBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextLogVo extends BaseInvo {

    private String text;

}
