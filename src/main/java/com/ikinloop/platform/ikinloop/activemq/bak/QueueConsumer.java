package com.ikinloop.platform.ikinloop.activemq.bak;

import com.ikinloop.platform.ikinloop.activemq.config.ActiveMqProperties;
import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;
import com.ikinloop.platform.ikinloop.activemq.mq.consumer.IMqhandler;
import com.ikinloop.platform.ikinloop.activemq.mq.consumer.MqCmd;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.annotation.JmsListenerAnnotationBeanPostProcessor;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 17:28
 **/
@Slf4j
@Component
public class QueueConsumer implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String,Object> queueMap = new HashMap<>();

    @Autowired
    private ActiveMqProperties activeMqProperties;

    public void addHandler(String cmdNo, IMqhandler iMqhandler){
        if(StringUtils.isEmpty(cmdNo) && queueMap.containsKey(cmdNo)){
            throw new IkinloopCreateQueueException();
        }
        queueMap.put(cmdNo,iMqhandler);
    }

    public QueueConsumer() {
    }


    /**
     * 监听指定Queue上的消息 并分到对应的handler处理此消息
     * @param DestinationName
     */
    public void start(String DestinationName) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId(DestinationName);
        endpoint.setDestination(DestinationName);
        endpoint.setMessageListener(message -> {
            try {
                MqCmd mqCmd = (MqCmd)((ObjectMessage) message).getObject();
                String text = ((TextMessage) message).getText();

                JSONObject jsonObject = JSONObject.fromObject(text);
                String cmdNo = jsonObject.get("cmdNo").toString();
                String cmdMsg = jsonObject.get("cmdMsg").toString();
                if(queueMap.containsKey(mqCmd.getCmdNo())){
                    IMqhandler iMqhandler = (IMqhandler)queueMap.get(mqCmd.getCmdNo());
                    iMqhandler.handle(mqCmd.getCmdNo(),mqCmd.getCmdMsg());
                }
            } catch (JMSException e) {
                log.error("消息读取失败"+e.getMessage());
            }
        });
        try {
            DefaultJmsListenerContainerFactory beanFactory =  this.createJmsListenerContainerFactory(activeMqProperties.getUser(), activeMqProperties.getPassword(), activeMqProperties.getBrokerUrl());
            JmsListenerAnnotationBeanPostProcessor bean = applicationContext.getBean(JmsListenerAnnotationBeanPostProcessor.class);
            Field endpointRegistry = JmsListenerAnnotationBeanPostProcessor.class.getDeclaredField("endpointRegistry");
            endpointRegistry.setAccessible(true);
            JmsListenerEndpointRegistry registry = (JmsListenerEndpointRegistry) endpointRegistry.get(bean);
            registry.registerListenerContainer(endpoint,beanFactory, true);
        } catch (NoSuchFieldException | IllegalAccessException |IllegalStateException e) {
            log.error("MQ配置连接失败--->>>"+e.getMessage());
        }


    }

    /**
     * 通过参数创建工厂
     * @param DestinationName
     * @param url
     * @param username
     * @param password
     */
    private DefaultJmsListenerContainerFactory createJmsListenerContainerFactory(String USERNAME,String PASSWORD,String BROKEN_URL){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        ActiveMQConnectionFactory af = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
        //异步发送
        af.setUseAsyncSend(true);
        //消费者预取
        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(1000);
        prefetchPolicy.setMaximumPendingMessageLimit(100000);
        af.setPrefetchPolicy(prefetchPolicy);
        af.setTrustAllPackages(true);//可信任的反序列包
        //批量确认
        af.setOptimizeAcknowledge(true);
        //重试次数
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        af.setRedeliveryPolicy(redeliveryPolicy);

        connectionFactory.setTargetConnectionFactory(af);
        connectionFactory.setSessionCacheSize(100);

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1");
        return factory;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
