package com.ikinloop.platform.ikinloop.activemq.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-26 16:14
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMqProperties {

    private String brokerUrl;
    private String user;
    private String password;
    private int deliveryMode;
    private String destinationName;

    public static final String CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "clusteringJmsListenerContainerFactory";

    //非持久化 JmsMessagingTemplate
    public static final String CLUSTERING_JMS_TEMPLATE_BEAN_NAME_1 = "clusteringJmsTemplate";

    //持久化 JmsMessageingTemplate
    public static final String CLUSTERING_JMS_TEMPLATE_BEAN_NAME_2 = "clusteringJmsTemplate";

    public static final String BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "broadcastJmsListenerContainerFactory";

    public static final String BROADCAST_JMS_TEMPLATE_BEAN_NAME = "broadcastJmsTemplate";

    // ========== 集群消费 =========

    @Bean(CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public DefaultJmsListenerContainerFactory clusteringJmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer, @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean(CLUSTERING_JMS_TEMPLATE_BEAN_NAME_1)
    public JmsMessagingTemplate clusteringJmsTemplate1(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        // 创建 JmsTemplate 对象
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        //非持久化
        template.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 创建 JmsMessageTemplate
        return new JmsMessagingTemplate(template);
    }

    @Bean(CLUSTERING_JMS_TEMPLATE_BEAN_NAME_2)
    public JmsMessagingTemplate clusteringJmsTemplate2(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        // 创建 JmsTemplate 对象
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        //持久化
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 创建 JmsMessageTemplate
        return new JmsMessagingTemplate(template);
    }

    // ========== 广播消费 ==========

    @Bean(BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public DefaultJmsListenerContainerFactory broadcastJmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer, @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean(BROADCAST_JMS_TEMPLATE_BEAN_NAME)
    public JmsMessagingTemplate broadcastJmsTemplate(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        // 创建 JmsTemplate 对象
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true);
        // 创建 JmsMessageTemplate
        return new JmsMessagingTemplate(template);
    }

}
