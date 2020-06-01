package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 15:30
 **/
@Component
public class PublishProducer {

    @Resource(name = ActiveMqProperties.BROADCAST_JMS_TEMPLATE_BEAN_NAME)
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void syncSend(String topicName,String message){
        //同步发送消息
        jmsMessagingTemplate.convertAndSend(topicName,message);
    }
}
