package com.ghrk.provider.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ghrk.common.entity.CaptchaEntity;
import com.ghrk.common.service.CaptchaService;
import com.ghrk.common.utils.DateUtils;
import com.ghrk.provider.dao.CaptchaDao;
import com.ghrk.common.exception.GlobalException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;

/**
 * 验证码
 */
@Component
@DubboService
public class CaptchaServiceImpl extends ServiceImpl<CaptchaDao, CaptchaEntity> implements CaptchaService {


    @Autowired
    private DefaultKaptcha kaptcha;

    @Override
    public String getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new GlobalException("uuid不能为空");
        }
        // 生成验证码文本
        String code = kaptcha.createText();
        CaptchaEntity captchaEntity = new CaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        this.save(captchaEntity);

        // 生成验证码图片
        BufferedImage image = kaptcha.createImage(code);

        // 将图片转为Base64编码的字符串
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public boolean validate(String uuid, String code) {
        CaptchaEntity captchaEntity = this.getOne(new QueryWrapper<CaptchaEntity>().eq("uuid", uuid));
        if(captchaEntity == null){
            return false;
        }

        //删除验证码
        //this.removeById(uuid);
        // 判断验证码是否正确，并且判断是否过期
        return captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis();
    }
}
