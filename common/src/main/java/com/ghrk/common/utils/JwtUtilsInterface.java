package com.ghrk.common.utils;

import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtUtilsInterface {

    public String generateToken(String userId);

    public Claims getClaimByToken(String token);

    public boolean isTokenExpired(Date expiration);

    public String getSecret();

    public void setSecret(String secret);

    public long getExpire();

    public void setExpire(long expire);

    public String getHeader();

    public void setHeader(String header);

}
