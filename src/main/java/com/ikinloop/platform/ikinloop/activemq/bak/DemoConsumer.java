package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.bean.DemoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 11:48
 **/
@Component
public class DemoConsumer {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = DemoMessage.QUEUE)
    public void onMessage(DemoMessage message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
