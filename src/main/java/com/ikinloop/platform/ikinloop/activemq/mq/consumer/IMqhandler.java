package com.ikinloop.platform.ikinloop.activemq.mq.consumer;

import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 18:01
 **/

public interface IMqhandler {
    public void handle(String cmdNo,String cmdMsg)throws IkinloopCreateQueueException;
}
