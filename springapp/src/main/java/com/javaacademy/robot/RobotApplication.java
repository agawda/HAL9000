package com.javaacademy.robot;

import com.javaacademy.robot.logger.ServerLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RobotApplication {

	public static void main(String[] args) {
		ServerLogger.initializeLogger();
		SpringApplication.run(RobotApplication.class, args);
	}
}
