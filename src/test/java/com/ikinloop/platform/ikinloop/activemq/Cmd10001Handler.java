package com.ikinloop.platform.ikinloop.activemq;

import com.ikinloop.platform.ikinloop.activemq.mq.consumer.IMqhandler;
import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 19:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformIkinloopActivemqApplication.class)
public class Cmd10001Handler implements IMqhandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void handle(String cmdNo, String cmdMsg) throws IkinloopCreateQueueException {
        logger.info("CmdNo:{} ===== CmdMsg:{}",cmdNo,cmdMsg);
    }
}
