package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.mq.consumer.TopicConsumer;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 17:29
 **/

public class TopicConsumerTest {

    public static void main(String[] args) throws Exception {
        TopicConsumer mqConsumer = new TopicConsumer("topic_no_persistent");
        Cmd10001Handler handler = new Cmd10001Handler();
        mqConsumer.addHandler("cmd_11111",handler);
        mqConsumer.start();
        System.in.read();
    }
}
