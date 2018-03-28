package com.lysyi.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@SpringBootApplication
@EnableSwagger2
@ComponentScan
public class N26ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(N26ChallengeApplication.class, args);
	}
}
