package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.bean.DemoMessage;
import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 11:46
 **/
@Component
public class DemoProducer {

    @Resource(name = ActiveMqProperties.CLUSTERING_JMS_TEMPLATE_BEAN_NAME_2)
    private JmsMessagingTemplate jmsTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo01Message 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        jmsTemplate.convertAndSend(DemoMessage.QUEUE, message);
    }

    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }
}
