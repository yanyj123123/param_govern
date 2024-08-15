package com.ghrk.provider.utils;

import com.ghrk.common.utils.JwtUtilsInterface;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 */

@Component
@DubboService
public class JwtUtils implements JwtUtilsInterface {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value($"globel.jwt.secret")
    private String secret;
    @Value($"globel.jwt.secret")
    private long expire;
    @Value($"globel.jwt.secret")
    private String header;

    /**
     * 生成jwt token
     */
    @Override
    public String generateToken(String userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    @Override
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public long getExpire() {
        return expire;
    }

    @Override
    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public void setHeader(String header) {
        this.header = header;
    }
}
