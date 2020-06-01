package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.bak.JmsProducerQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformIkinloopActivemqApplication.class)
public class PlatformIkinloopActivemqApplicationTests {

    @Autowired
    private JmsProducerQueue jmsProducerQueue;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        jmsProducerQueue.sendMessage();
    }
}
