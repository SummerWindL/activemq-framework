package com.ikinloop.platform.ikinloop.activemq.service.impl;

import com.ikinloop.platform.ikinloop.activemq.bean.IConsumer;
import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;
import com.ikinloop.platform.ikinloop.activemq.service.AbstractMqService;
import com.ikinloop.platform.ikinloop.activemq.service.bean.IContent;
import org.springframework.stereotype.Service;


/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-26 15:59
 **/
@Service
public class AbstractMqServiceImpl  extends AbstractMqFactory implements AbstractMqService {


    @Override
    public boolean createQueueMq() throws IkinloopCreateQueueException {
        return false;
    }

    @Override
    public boolean createTopicMq() throws IkinloopCreateQueueException {
        return false;
    }

    @Override
    public boolean publishMsg(IContent iContent) {
        return false;
    }

    @Override
    public boolean addListener(IConsumer iConsumer) {
        return false;
    }
}
