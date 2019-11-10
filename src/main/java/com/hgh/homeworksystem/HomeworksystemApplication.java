package com.hgh.homeworksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeworksystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworksystemApplication.class, args);
	}

}
