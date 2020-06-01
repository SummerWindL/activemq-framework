package com.ikinloop.platform.ikinloop.activemq.config;

import com.ikinloop.platform.ikinloop.activemq.service.impl.AbstractMqFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-26 17:17
 **/
@Configuration
@EnableConfigurationProperties(value = { ActiveMqProperties.class})
public class ActiveMqAutoConfiguration {

    @Bean
    public AbstractMqFactory abstractMqFactory(){
        return new AbstractMqFactory();
    }
}
