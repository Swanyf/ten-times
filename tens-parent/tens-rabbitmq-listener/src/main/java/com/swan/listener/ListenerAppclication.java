package com.swan.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ListenerAppclication {

    public static void main(String[] args) {
        SpringApplication.run(ListenerAppclication.class,args);
    }
}
