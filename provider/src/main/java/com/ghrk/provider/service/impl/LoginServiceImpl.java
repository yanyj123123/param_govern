package com.ghrk.provider.service.impl;

import org.springframework.beans.factory.annotation.Value;

public class LoginServiceImpl {
    @Value("${global.jwt.secret}")
    private String secret;
    @Value("${global.jwt.expire}")
    private long expire;
    @Value("${global.jwt.header}")
    private String header;
}
