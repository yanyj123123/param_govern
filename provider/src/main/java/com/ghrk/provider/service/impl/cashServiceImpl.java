package com.ghrk.provider.service.impl;

import org.springframework.beans.factory.annotation.Value;

public class cashServiceImpl {
    @Value("${global.cashinfo.cashNum}")
    private String cashNum;
}
