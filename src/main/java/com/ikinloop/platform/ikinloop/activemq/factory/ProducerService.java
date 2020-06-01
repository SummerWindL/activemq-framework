package com.ikinloop.platform.ikinloop.activemq.factory;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 11:48
 **/
public interface ProducerService {
    void producer();

    void pushMsg(String cmdNo,String message);
}
