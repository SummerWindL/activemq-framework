package com.ikinloop.platform.ikinloop.activemq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 15:26
 **/
@Data
public class BroadcastMessage implements Serializable {

    public static final String TOPIC = "TOPIC_BROADCASTING";

    /**
     * 编号
     */
    private Integer id;
}
