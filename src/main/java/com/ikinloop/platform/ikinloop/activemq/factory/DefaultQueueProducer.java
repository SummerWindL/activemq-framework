package com.ikinloop.platform.ikinloop.activemq.factory;

import org.springframework.stereotype.Service;

/**
 * @program: platform-ikinloop-activemq
 * @description: 默认队列生产者
 * @author: fuyl
 * @create: 2020-05-28 11:49
 **/
@Service
public class DefaultQueueProducer implements ProducerService{

    @Override
    public void producer() {

    }

    @Override
    public void pushMsg(String cmdNo, String message) {

    }
}
