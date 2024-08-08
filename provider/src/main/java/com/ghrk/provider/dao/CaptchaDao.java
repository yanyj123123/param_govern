package com.ghrk.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghrk.common.entity.CaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 */
@Mapper
public interface CaptchaDao extends BaseMapper<CaptchaEntity> {

}
