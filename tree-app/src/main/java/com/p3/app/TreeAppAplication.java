package com.p3.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.p3.app",
        "com.p3.engine"
})
public class TreeAppAplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeAppAplication.class, args);
    }
}
