package br.com.fiap.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PosTechFastFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosTechFastFoodApplication.class, args);
	}

}
