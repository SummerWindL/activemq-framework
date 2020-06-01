package com.ikinloop.platform.ikinloop.activemq.factory;

import org.springframework.stereotype.Service;

/**
 * @program: platform-ikinloop-activemq
 * @description: 动态队列生产者(通过入参生成JmsMessageTemplate)
 * @author: fuyl
 * @create: 2020-05-28 11:54
 **/
@Service
public class DynamicQueueProducer implements ProducerService{

    @Override
    public void producer() {

    }

    @Override
    public void pushMsg(String cmdNo, String message) {

    }
}
