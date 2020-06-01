package com.ikinloop.platform.ikinloop.activemq.mq.consumer;

import com.ikinloop.platform.ikinloop.activemq.exception.IkinloopCreateQueueException;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.util.StringUtils;

import javax.jms.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:13
 **/

public class QueueConsumer {
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String BROKEN_URL = "";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer = null;
    private Map<String,Object> handleMap = new HashMap<>();
    private String queueName = "";
    ExecutorService threadPool = Executors.newFixedThreadPool(1);


    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    public QueueConsumer(String queueName){
        this.queueName = queueName;

    }

    public void destory() throws JMSException {
        if(consumer !=null ){
            consumer.close();
        }
        if(session != null){
            session.close();
        }
        if(connection != null){
            connection.close();
        }
        consumer = null;
        session = null;
        connection = null;
    }
    private void initConsumer(String queueName) throws Exception{
        try{
            Properties properties = new Properties();
            InputStream resourceAsStream = Object.class.getResourceAsStream("/application-dev.properties");
            properties.load(resourceAsStream);
            USERNAME = (String)properties.get("user");
            PASSWORD = (String)properties.get("password");
            BROKEN_URL = (String)properties.get("broker-url");
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            connection  = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            if(threadLocal.get() !=null){
                consumer = threadLocal.get();
            }else{
                consumer = session.createConsumer(queue);
                threadLocal.set(consumer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.destory();
        }
    }
    private void reviceMsg() throws JMSException {
        try{
            TextMessage msg = (TextMessage) consumer.receive();
            msg.acknowledge();
            JSONObject jsonObject = JSONObject.fromObject(msg.getText());
            String cmdNo = jsonObject.get("cmdNo").toString();
            String cmdMsg = jsonObject.get("cmdMsg").toString();
            if (handleMap.containsKey(cmdNo)) {
                IMqhandler iMqhandler = (IMqhandler) handleMap.get(cmdNo);
                iMqhandler.handle(cmdNo, cmdMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.destory();
        }
    }
    public void start() throws JMSException {

        try {
            this.initConsumer(queueName);
            Future<?> submit = threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {

                            Thread.sleep(20);
                            try {
                                reviceMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.destory();
        }
    }
    public void addHandler(String cmdNo,IMqhandler iMqhandler){
        if(StringUtils.isEmpty(cmdNo) && handleMap.containsKey(cmdNo)){
            throw new IkinloopCreateQueueException();
        }
        handleMap.put(cmdNo,iMqhandler);
    }
}
