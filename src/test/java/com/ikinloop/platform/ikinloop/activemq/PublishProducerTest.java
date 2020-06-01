package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.factory.ProducerGenerateFactory;
import com.ikinloop.platform.ikinloop.activemq.factory.ProducerService;
import com.ikinloop.platform.ikinloop.activemq.bak.PublishProducer;
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
 * @create: 2020-05-27 15:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformIkinloopActivemqApplication.class)
public class PublishProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PublishProducer producer;

    @Test
    public void mock1() throws InterruptedException {
        new CountDownLatch(1).await();
    }

    @Test
    public void testSyncSend1() throws InterruptedException {

        ProducerGenerateFactory factory = new ProducerGenerateFactory();
        ProducerService producer = factory.getProducer("DefaultPublishProducer");
        producer.producer();
        for (int i = 0; i < 3; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
//            producer.syncSend("1","1212");
            producer.pushMsg("cmd_10001","mesage");
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
