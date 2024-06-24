package br.com.fabioalvaro.sorteiocore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SorteiocoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SorteiocoreApplication.class, args);
	}

}
