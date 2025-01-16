package br.com.challenge_4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Challenge4Application {

	public static void main(String[] args) {
		SpringApplication.run(Challenge4Application.class, args);
	}

}
