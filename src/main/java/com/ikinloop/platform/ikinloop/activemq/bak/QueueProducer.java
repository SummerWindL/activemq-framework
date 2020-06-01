package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import com.ikinloop.platform.ikinloop.activemq.mq.consumer.MqCmd;
import net.sf.json.JSONObject;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 13:47
 **/
@Component
public class QueueProducer {

    @Resource(name = ActiveMqProperties.CLUSTERING_JMS_TEMPLATE_BEAN_NAME_2)
    private JmsMessagingTemplate jmsTemplate;


    public void pushMsg(String queueName ,String cmdNo,String message) {
        // 创建 ClusteringMessage 消息

        // 同步发送消息
        MqCmd mqCmd = new MqCmd();
        mqCmd.setCmdNo(cmdNo);
        mqCmd.setCmdMsg(message);
        JSONObject jsonObject = JSONObject.fromObject(mqCmd);
        jmsTemplate.convertAndSend(queueName, mqCmd);
    }


}
