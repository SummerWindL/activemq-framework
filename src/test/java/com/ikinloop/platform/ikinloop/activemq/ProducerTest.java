package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.mq.produce.QueueProducer;

import java.io.IOException;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:28
 **/

public class ProducerTest {

    public static void main(String[] args) throws Exception {
        QueueProducer mqProducer = new QueueProducer("cmd_10001");
        for(int i = 1;i<1 ;i++){
            mqProducer.pushMsg("cmd_11111","11111");
        }
        System.out.println("消息发送完成====");

    }

}
