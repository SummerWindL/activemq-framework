package com.ikinloop.platform.ikinloop.activemq.factory;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import com.ikinloop.platform.ikinloop.activemq.mq.consumer.MqCmd;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @program: platform-ikinloop-activemq
 * @description: 默认发布/订阅生产者
 * @author: fuyl
 * @create: 2020-05-28 11:52
 **/
@Component
public class DefaultPublishProducer implements ProducerService, InitializingBean {

    private Logger log = LoggerFactory.getLogger(getClass());
    private JmsMessagingTemplate jmsMessagingTemplate = null;

    private Connection connection = null;

    //部分固定参数
    @Autowired
    ActiveMqProperties activeMqProperties;

    //1.表示非持久化 2.持久化
    private int deliveryMode;
    //目标队列
    private String destinationName;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public DefaultPublishProducer() {
    }

    public DefaultPublishProducer(int deliveryMode, String destinationName) {
        this.deliveryMode = deliveryMode;
        this.destinationName = destinationName;
        System.out.println("Construct被执行了");
    }


    @PostConstruct
    public void init(){
        log.info("初始化方法被执行了");
        //初始化连接参数
        jmsMessagingTemplate = this.createDefaultPublishConnection();
    }

    @PreDestroy
    public void destory(){
        this.disconnectServer();
    }

    public void disconnectServer() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
    /**
     * 初始化DefaultPublish连接 获取JmsMessagingTemplate
     */
    private JmsMessagingTemplate createDefaultPublishConnection(){
        log.info("创建连接开始");
        //1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMqProperties.getUser(),activeMqProperties.getPassword(),activeMqProperties.getBrokerUrl());
        try{
//            ExecutorService service = Executors.newFixedThreadPool(1);
//            Future<?> submit = service.submit(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        //2.创建Connection
//                        connection = connectionFactory.createConnection();
//                        //3.启动连接
//                        connection.start();
//                        log.info("Connect to Server.");
//                    } catch (Exception e) {
//                        log.error(e.getMessage(), e);
//                    }
//                }
//            });

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(111111);
//
//                }
//            }).start();
            connection = connectionFactory.createConnection();
            //3.启动连接
            connection.start();

            //4.创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标
            Destination destination = session.createQueue("11111");
            JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
            jmsTemplate.setDefaultDestination(destination);
            return new JmsMessagingTemplate(jmsTemplate);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return new JmsMessagingTemplate(); //default jmstemplate
    }

    @Override
    public void producer() {
        System.out.println("defaultPublish");
    }

    /**
     * 发送消息
     * @param cmdNo
     * @param message
     */
    @Override
    public void pushMsg(String cmdNo,String message){
        log.info("开始发送消息");
        // 同步发送消息
        MqCmd mqCmd = new MqCmd();
        mqCmd.setCmdNo(cmdNo);
        mqCmd.setCmdMsg(message);
        jmsMessagingTemplate.convertAndSend(destinationName,mqCmd);
    }

}
