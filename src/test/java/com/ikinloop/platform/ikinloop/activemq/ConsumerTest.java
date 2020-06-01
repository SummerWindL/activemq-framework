package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.mq.consumer.QueueConsumer;


/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:32
 **/

public class ConsumerTest {
    public static void main(String[] args) throws Exception {
        QueueConsumer mqConsumer = new QueueConsumer("cmd_10001");
        Cmd10001Handler handler = new Cmd10001Handler();
        mqConsumer.addHandler("cmd_11111",handler);
        mqConsumer.start();
        System.in.read();
    }
}
