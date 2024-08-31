package com.oschool.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.oschool.student.*"})
public class StudentApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/student");
		SpringApplication.run(StudentApplication.class, args);
	}


}
