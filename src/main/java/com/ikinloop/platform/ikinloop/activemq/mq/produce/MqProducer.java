package com.ikinloop.platform.ikinloop.activemq.mq.produce;


import com.ikinloop.platform.ikinloop.activemq.common.MqConst;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 20:34
 **/

public class MqProducer {
    private String mqType ="";
    private String mqName ="";
    private boolean persistentflag = true;
    public MqProducer(String mqType, String mqName){
        this.mqType = mqType;
        this.mqName = mqName;
    }

    public MqProducer(String mqType, String mqName,boolean persistentflag){
        this.mqType = mqType;
        this.mqName = mqName;
        this.persistentflag = persistentflag;
    }

    private void pushTopicMsg(String cmdNo,String message) {
        try{
            TopicProducer mqProducer = new TopicProducer(this.mqName,persistentflag);
             mqProducer.pushMsg(cmdNo,message);
            mqProducer.destory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pushQueueMsg(String cmdNo,String message) {
        try{
            QueueProducer mqProducer = new QueueProducer(this.mqName,persistentflag);
            mqProducer.pushMsg(cmdNo,message);
            mqProducer.destory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pushMsg(String cmdNo,String message){
        if(MqConst.TOPIC.equals(mqType)){
            this.pushTopicMsg(cmdNo,message);
        }else if(MqConst.QUEUE.equals(mqType)){
            this.pushQueueMsg(cmdNo,message);
        }
    }
}
