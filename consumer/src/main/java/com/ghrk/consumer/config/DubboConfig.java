package com.ghrk.consumer.config;

import com.ghrk.consumer.filter.DubboLoggerFilter;
import org.apache.dubbo.rpc.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {
    @Bean
    public Filter dubboLoggerFilter(){
        return new DubboLoggerFilter();
    }
}
