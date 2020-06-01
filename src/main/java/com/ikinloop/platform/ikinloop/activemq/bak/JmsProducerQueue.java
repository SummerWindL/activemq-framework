package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.util.UUID;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 10:07
 **/
@Component
public class JmsProducerQueue {

    @Resource(name = ActiveMqProperties.CLUSTERING_JMS_TEMPLATE_BEAN_NAME_2)
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void sendMessage(){
        jmsMessagingTemplate.convertAndSend(queue,"******"+ UUID.randomUUID().toString().substring(0,6));
    }
}
