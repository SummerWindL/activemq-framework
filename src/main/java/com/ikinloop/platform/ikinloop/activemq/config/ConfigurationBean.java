package com.ikinloop.platform.ikinloop.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 10:00
 **/
@Component
@EnableJms
public class ConfigurationBean {

    @Value("${queuename}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }
}
