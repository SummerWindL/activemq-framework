package com.ikinloop.platform.ikinloop.activemq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: platform-ikinloop-activemq
 * @description: 集群消费 消息 在同一个Consumer Group 中同时创建消费该 Queue 的 Consumer 点对点消费
 * @author: fuyl
 * @create: 2020-05-27 11:59
 **/
@Data
public class QueueMessage implements Serializable {
//    public static final String QUEUE = "QUEUE_CLUSTERING";


    public String QUEUE= "";
    /**
     * 编号
     */
    public Integer id;
}
