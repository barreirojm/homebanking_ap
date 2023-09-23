package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.AccountType;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.RoleType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccount (Authentication auth){
        return accountService.getAccount(auth);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount (Authentication auth){

        Client client  = clientService.getClientByAuth(auth);

        if (client == null || !client.getRole().equals(RoleType.CLIENT)) {
            return new ResponseEntity<>("Only authenticated clients can create accounts.", HttpStatus.FORBIDDEN);
            }

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("You cannot create more than 3 accounts.", HttpStatus.FORBIDDEN);
            }

        String number = "VIN-" + generateRandomAccountNumber();

        if (accountService.getAccountByNumber(number) != null) {

            number = generateRandomAccountNumber();
        }

        Account account = new Account(number, LocalDate.now(),0.0, true, AccountType.SAVINGS);

        account.setHolder(client);
        accountService.saveAccount(account);

        client.getAccounts().add(account);

        clientService.saveClient(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private String generateRandomAccountNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    ////////////////////////////////////////

    @PatchMapping(path = "/clients/current/accounts")
    public ResponseEntity<String> deleteAccount (@RequestParam Long id, Authentication auth) {

        Client client = clientService.getClientByAuth(auth);

        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok("Account deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
}


