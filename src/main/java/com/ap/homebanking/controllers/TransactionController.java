package com.ap.homebanking.controllers;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.ClientService;
import com.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transaction
            (@RequestParam String accountFromNumber, @RequestParam String accountToNumber, @RequestParam double amount, @RequestParam String description,
             Authentication auth) {

        if (amount <= 0) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }

        if (accountFromNumber.isEmpty()) {
            return new ResponseEntity<>("Missing source account number", HttpStatus.FORBIDDEN);
        }

        if (accountToNumber.isEmpty()) {
            return new ResponseEntity<>("Missing destination account number", HttpStatus.FORBIDDEN);
        }

        if (accountFromNumber.equals(accountToNumber)) {
            return new ResponseEntity<>("Source and destination account numbers cannot be the same.", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.getClientByAuth(auth);

        if (client == null || !client.getRole().equals(RoleType.CLIENT)) {
            return new ResponseEntity<>("Only authenticated clients can perform transactions.", HttpStatus.FORBIDDEN);
        }

        Account fromAccount = accountService.getAccountByNumber(accountFromNumber);
        Account toAccount = accountService.getAccountByNumber(accountToNumber);

        if (fromAccount == null) {
            return new ResponseEntity<>("Source account not found.", HttpStatus.FORBIDDEN);
        }

        if (toAccount == null) {
            return new ResponseEntity<>("Destination account not found.", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(fromAccount)){
            return new ResponseEntity<>("The source account doesn't belong to the authenticated client.", HttpStatus.FORBIDDEN);
        }

        if (fromAccount.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient balance in the source account.", HttpStatus.BAD_REQUEST);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, description + " - DEBIT: " + accountFromNumber, LocalDateTime.now());
        debitTransaction.setAccount(fromAccount);
        transactionService.saveTransaction(debitTransaction);

        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, +amount, description + " - CREDIT: " + accountToNumber, LocalDateTime.now());
        creditTransaction.setAccount(toAccount);
        transactionService.saveTransaction(creditTransaction);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountService.saveAccount(fromAccount);
        accountService.saveAccount(toAccount);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
