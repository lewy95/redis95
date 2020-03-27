package com.xzxy.lewy.redis95;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author lewy
 */
@EnableCaching
@SpringBootApplication
public class Redis95Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis95Application.class, args);
    }

}
