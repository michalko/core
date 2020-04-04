package com.brain2.demo;

import java.util.function.Consumer;

import com.brain2.demo.repos.PostRepo;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// @EnableCaching
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	TopicRepo topicRepo;

	@Autowired
	PostRepo postRepo;

	@Bean
	public CommandLineRunner demo(TopicRepo repository, TopicTagsRepo repository2) {
		return (args) -> {
			// topicRepo.findAll().forEach(systout());
			// Iterables.limit(topicRepo.findAll(), 10).forEach(systout());
			// Iterables.limit(postRepo.findAll(), 10).forEach(systout());
		};
	}

	private Consumer<? super Object> systout() {
		return o -> System.out.println(o);
	}

}
