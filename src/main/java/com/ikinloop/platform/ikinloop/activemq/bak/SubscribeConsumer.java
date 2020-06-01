package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import com.ikinloop.platform.ikinloop.activemq.message.BroadcastMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 15:33
 **/
@Component
public class SubscribeConsumer {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = BroadcastMessage.TOPIC,
            containerFactory = ActiveMqProperties.BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public void onMessage(BroadcastMessage message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
