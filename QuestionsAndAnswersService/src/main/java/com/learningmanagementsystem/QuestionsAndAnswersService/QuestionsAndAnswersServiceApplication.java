package com.learningmanagementsystem.QuestionsAndAnswersService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class QuestionsAndAnswersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionsAndAnswersServiceApplication.class, args);
	}

}
