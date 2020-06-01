package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.bak.QueueConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 17:57
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformIkinloopActivemqApplication.class)
public class QueueConsumerTest {
    @Autowired
    private QueueConsumer queueConsumer;

    @Test
    public void test() throws IOException {
        Cmd10001Handler handler = new Cmd10001Handler();
        queueConsumer.addHandler("cmd_10001",handler);
        queueConsumer.start("1111");
        System.in.read();
    }
}
