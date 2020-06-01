package com.ikinloop.platform.ikinloop.activemq.mq.produce;

import com.ikinloop.platform.ikinloop.activemq.mq.consumer.MqCmd;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:02
 **/

public class QueueProducer {

    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private MessageProducer producer = null;
    private Session session = null;
    private static String USERNAME ="";
    private static String PASSWORD = "";
    private static String BROKEN_URL = "";

    public QueueProducer(String queueName) throws Exception {

        initProducer(queueName,true);
    }

    public QueueProducer(String queueName,boolean persistentflag) throws Exception {

        initProducer(queueName,persistentflag);
    }

    public void destory() throws JMSException {
        if(producer !=null ){
            producer.close();
        }
        if(session != null){
            session.close();
        }
        if(connection != null){
            connection.close();
        }
        producer = null;
        session = null;
        connection = null;
    }

    private void initProducer(String queueName,boolean persistentflag) throws Exception{

        try{
            Properties properties = new Properties();
            InputStream resourceAsStream = Object.class.getResourceAsStream("/application-dev.properties");
            properties.load(resourceAsStream);
            USERNAME = (String)properties.get("user");
            PASSWORD = (String)properties.get("password");
            BROKEN_URL = (String)properties.get("broker-url");
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            connection = connectionFactory.createConnection();
            //3.启动连接
            connection.start();

            //4.创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标
            Destination destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            if(persistentflag){
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            }else {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.destory();
        }



    }

    public void pushMsg(String cmdNo,String message) throws Exception {
        try{
            MqCmd mqCmd = new MqCmd();
            mqCmd.setCmdNo(cmdNo);
            mqCmd.setCmdMsg(message);
            JSONObject jsonObject = JSONObject.fromObject(mqCmd);
            TextMessage msg = session.createTextMessage(jsonObject.toString());
            producer.send(msg);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.destory();
        }

    }
}
