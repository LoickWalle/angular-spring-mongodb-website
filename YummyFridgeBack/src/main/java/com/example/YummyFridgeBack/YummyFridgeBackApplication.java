package com.example.YummyFridgeBack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class YummyFridgeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(YummyFridgeBackApplication.class, args);
	}

}
