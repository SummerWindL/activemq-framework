package com.ikinloop.platform.ikinloop.activemq.mq.consumer;


import com.ikinloop.platform.ikinloop.activemq.common.MqConst;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 20:47
 **/

public class MqConsumer {
    private String mqType ="";
    private String mqName ="";
    private QueueConsumer queueConsumer= null;
    private TopicConsumer topicConsumer = null;
    public MqConsumer(String mqType, String mqName){
        this.mqType = mqType;
        this.mqName = mqName;
        queueConsumer = new QueueConsumer(mqName);
        topicConsumer = new TopicConsumer(mqName);
    }


    public void addHandler(String cmdNo, IMqhandler iMqhandler){
        if(MqConst.TOPIC.equals(mqType)){
            topicConsumer.addHandler(cmdNo,iMqhandler);
        }else if(MqConst.QUEUE.equals(mqType)){
            queueConsumer.addHandler(cmdNo,iMqhandler);
        }
    }

    public void start(){
        try{
            if(MqConst.TOPIC.equals(mqType)){
                topicConsumer.start();
            }else if(MqConst.QUEUE.equals(mqType)){
                queueConsumer.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
