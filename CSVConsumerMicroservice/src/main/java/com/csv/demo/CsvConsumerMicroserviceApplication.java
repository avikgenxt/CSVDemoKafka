package com.csv.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@Configuration
@ComponentScan("com")
@EnableWebMvc
public class CsvConsumerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvConsumerMicroserviceApplication.class, args);
	}

}
