package com.ghrk.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ProviderApplication {
    private static final Logger logger= LogManager.getLogger(ProviderApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(ProviderApplication.class, args);
        logger.error("This is an error message");
    }

}
