package com.ap.homebanking;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Juan Manuel", "Barreiro","jmb@mail.com");
			Client client3 = new Client("Denise", "Marelli", "denu@mail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN003", LocalDate.of(2022, 1, 11), 250000);
			Account account4 = new Account("VIN004", LocalDate.of(2020, 7, 20), 25000000);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,1000,"Venta_001",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-5000,"Compra_001", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 2500, "Venta_002", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3000,"Compra_002",LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.DEBIT,-2000,"Compra_003",LocalDateTime.now());

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

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36, client2, loan3);
			ClientLoan clientLoan5 = new ClientLoan(250000, 60, client3, loan1);
			ClientLoan clientLoan6 = new ClientLoan(40000, 12, client3, loan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			clientLoanRepository.save(clientLoan5);
			clientLoanRepository.save(clientLoan6);



		});
	}

}
