package com.ghrk.provider.service.impl;

import org.springframework.beans.factory.annotation.Value;

public class CardServiceImpl {
    @Value("${global.cardinfo.cardCity}")
    private String cardCity;
    @Value("${global.cardinfo.cardLevel}")
    private String cardLevel;
}
