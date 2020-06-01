package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.bak.QueueConsumer;
import com.ikinloop.platform.ikinloop.activemq.bak.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 14:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformIkinloopActivemqApplication.class)
public class QueueProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QueueProducer producer;

    @Test
    public void mock1() throws InterruptedException {
        // 阻塞等待，保证消费
        QueueConsumer queueConsumer = new QueueConsumer();
        queueConsumer.start("1111");
        new CountDownLatch(1).await();
    }

    @Test
    public void testSyncSend() throws InterruptedException {


        // 发送 3 条消息
        for (int i = 0; i < 3; i++) {
            producer.pushMsg("1111","cmd_10001","mesage");
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", 1111);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
