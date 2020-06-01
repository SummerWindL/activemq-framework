package com.ikinloop.platform.ikinloop.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class PlatformIkinloopActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformIkinloopActivemqApplication.class, args);
    }

}
