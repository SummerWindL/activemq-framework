package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.mq.produce.TopicProducer;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 17:28
 **/

public class TopicProducerTest {
    public static void main(String[] args) throws Exception {
        TopicProducer mqProducer = new TopicProducer("topic_no_persistent",false);
        for(int i = 1;i<=10 ;i++){
            mqProducer.pushMsg("cmd_11111","11111");
        }
        System.out.println("消息发送完成====");
        mqProducer.destory();
    }
}
