package com.ap.homebanking;

import com.ap.homebanking.models.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ap.homebanking.repositories.ClientRepository;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Juan Manuel", "Barreiro","jmb@mail.com");
			Client client3 = new Client("Denise", "Marelli", "denu@mail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
		});
	}

}
