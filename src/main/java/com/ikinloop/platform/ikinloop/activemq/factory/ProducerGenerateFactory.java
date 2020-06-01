package com.ikinloop.platform.ikinloop.activemq.factory;

/**
 * @program: platform-ikinloop-activemq
 * @description: 生产者工厂类
 * @author: fuyl
 * @create: 2020-05-28 11:58
 **/

public class ProducerGenerateFactory {


    /**
     * 获取不同实例类
     */
    public ProducerService getProducer(String producerType){
        if(producerType == null){
            return null;
        }
        if(producerType.equalsIgnoreCase("DefaultPublishProducer")){
            return new DefaultPublishProducer(1,"11111");
        } else if(producerType.equalsIgnoreCase("DefaultQueueProducer")){
            return new DefaultQueueProducer();
        } else if(producerType.equalsIgnoreCase("DynamicPublishProducer")){
            return new DynamicPublishProducer();
        } else if(producerType.equalsIgnoreCase("DynamicQueueProducer")){
            return new DynamicQueueProducer();
        }
        return new DefaultQueueProducer(); //返回默认队列生产者
    }

}

class Test{
    public static void main(String[] args) {
        ProducerGenerateFactory factory = new ProducerGenerateFactory();
        ProducerService defaultPublishProducer = factory.getProducer("DefaultPublishProducer");
        defaultPublishProducer.producer();
    }
}
