package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

@EnableProcessApplication
@SpringBootApplication
public class MinimalCamundaApplication {
    public static void main(String... args) {
        SpringApplication.run(MinimalCamundaApplication.class, args);
    }
}