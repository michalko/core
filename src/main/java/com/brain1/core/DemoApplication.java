package com.brain1.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
// @EnableCaching
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			Object obj = "viola";
			if (obj instanceof String) {
				String str = (String) obj; 
				System.out.println(str);
			}
			// topicRepo.findAll().forEach(systout());
			// Iterables.limit(topicRepo.findAll(), 10).forEach(systout());
			// Iterables.limit(postRepo.findAll(), 10).forEach(systout());
		};
	}


}
