package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Account;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private boolean isActive;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(){

    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
        this.isActive = account.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
