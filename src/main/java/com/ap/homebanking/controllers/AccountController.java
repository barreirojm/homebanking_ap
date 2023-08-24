package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.RoleType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){

        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());

    }
    @RequestMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount (Authentication auth){

        Client client  = clientRepository.findByEmail(auth.getName());

        if (client == null || !client.getType().equals(RoleType.CLIENT)) {
            return new ResponseEntity<>("Only authenticated clients can create accounts.", HttpStatus.FORBIDDEN);
            }

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("You cannot create more than 3 accounts.", HttpStatus.FORBIDDEN);
            }

        String number = "VIN-" + generateRandomAccountNumber();

        Account account = new Account(number, LocalDate.now(),0.0);

        account.setHolder(client);
        accountRepository.save(account);

        client.getAccounts().add(account);
        clientRepository.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private String generateRandomAccountNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }
}


