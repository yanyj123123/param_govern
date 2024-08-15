package com.ghrk.provider.service.impl;

import org.springframework.beans.factory.annotation.Value;

public class LoginServiceImpl {
    @Value("${globel.jwt.secret}")
    private String secret;
    @Value("${globel.jwt.expire}")
    private long expire;
    @Value("${globel.jwt.header}")
    private String header;
}
