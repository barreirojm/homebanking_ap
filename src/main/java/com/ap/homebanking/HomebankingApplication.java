package com.ap.homebanking;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEnconder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("melba"), RoleType.CLIENT);
			Client client2 = new Client("Juan Manuel", "Barreiro","jmb@mindhub.com", passwordEnconder.encode("jmb"), RoleType.CLIENT);
			Client client3 = new Client("Denise", "Marelli", "denu@mindhub.com", passwordEnconder.encode("denu"), RoleType.CLIENT);
			Client admin = new Client("admin", "admin", "admin@mindhub.com", passwordEnconder.encode("admin"), RoleType.ADMIN);

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(admin);

			Account account1 = new Account("VIN-001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN-002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN-003", LocalDate.of(2022, 1, 11), 250000);
			Account account4 = new Account("VIN-004", LocalDate.of(2020, 7, 20), 25000000);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,1000,"sale_001",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-5000,"purchase_001", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 2500, "sale_002", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3000,"purchase_002",LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.DEBIT,-2000,"purchase_003",LocalDateTime.now());

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction5);
			account2.addTransaction(transaction2);
			account3.addTransaction(transaction3);
			account4.addTransaction(transaction4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);

			Loan loan1 = new Loan("Mortgage", 500000, List.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6, 12, 24));
			Loan loan3 = new Loan("Auto", 300000, List.of(12, 24, 36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12);
			ClientLoan clientLoan3 = new ClientLoan(100000, 24);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36);
			ClientLoan clientLoan5 = new ClientLoan(250000, 60);
			ClientLoan clientLoan6 = new ClientLoan(40000, 12);

			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan3);
			loan3.addClientLoan(clientLoan4);
			loan1.addClientLoan(clientLoan5);
			loan2.addClientLoan(clientLoan6);

			client1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			client3.addClientLoan(clientLoan5);
			client3.addClientLoan(clientLoan6);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			clientLoanRepository.save(clientLoan5);
			clientLoanRepository.save(clientLoan6);

			Card card1 = new Card((client1.getFirstName()).concat(" ").concat(client1.getLastName()), CardType.DEBIT, CardColor.GOLD, "1234-5678-9000-0000", 123, LocalDateTime.now(), LocalDateTime.now().plusYears(5), true);
			Card card2 = new Card((client1.getFirstName()).concat(" ").concat(client1.getLastName()), CardType.CREDIT, CardColor.TITANIUM, "1111-2222-3333-4444", 333, LocalDateTime.now(), LocalDateTime.now().plusYears(5), true);
			Card card3 = new Card((client2.getFirstName()).concat(" ").concat(client2.getLastName()), CardType.CREDIT, CardColor.SILVER, "0004-9874-0000-0490", 111, LocalDateTime.now(), LocalDateTime.now().plusYears(3), true);
			Card card4 = new Card((client3.getFirstName()).concat(" ").concat(client3.getLastName()), CardType.DEBIT, CardColor.GOLD, "2342-5234-5000-8795", 119, LocalDateTime.now(), LocalDateTime.now().plusYears(2), true);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			client3.addCard(card4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);

		});
	}

}
