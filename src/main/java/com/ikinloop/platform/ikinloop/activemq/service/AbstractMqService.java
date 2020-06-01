package com.ikinloop.platform.ikinloop.activemq.service;

import com.ikinloop.platform.ikinloop.activemq.bean.IConsumer;
import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;
import com.ikinloop.platform.ikinloop.activemq.service.bean.IContent;

/**
 * @program: platform-base
 * @description: 定义ActiveMq对外接口
 * @author: fuyl
 * @create: 2020-05-26 16:02
 **/
public interface AbstractMqService {
    boolean createQueueMq() throws IkinloopCreateQueueException;
    boolean createTopicMq() throws IkinloopCreateQueueException;
    boolean publishMsg(IContent iContent);
    boolean addListener(IConsumer iConsumer);
}
