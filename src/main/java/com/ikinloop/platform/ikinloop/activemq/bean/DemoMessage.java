package com.ikinloop.platform.ikinloop.activemq.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 11:45
 **/
@Data
public class DemoMessage implements Serializable {
    public static final String QUEUE = "QUEUE_DEMO_01";

    /**
     * 编号
     */
    private Integer id;

}
