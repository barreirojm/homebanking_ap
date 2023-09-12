package com.ap.homebanking.repositories;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void allAccountsWithClient(){
        List<Account> accounts = accountRepository.findAll();
        assertTrue(accounts.stream().allMatch(account -> account.getHolder() != null));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    public void existCardHolder(){
        List<Card> cards = cardRepository.findAll();
        assertTrue(cards.stream().allMatch(card -> card.getCardHolder() != null));
    }

    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void allClientsWithAccount() {
        List<Client> clients = clientRepository.findAll();
        assertTrue(clients.stream().allMatch(client -> client.getAccounts() != null));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void allTransactionsWithAccounts(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertTrue(transactions.stream().allMatch(transaction -> transaction.getAccount() != null));
    }


}

