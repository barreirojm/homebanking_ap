package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.RoleType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.ClientService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() { return clientService.getClients(); }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id){
        return clientService.getClient(id);
        }

    @GetMapping("/clients/current")
    public ClientDTO getByAuth(Authentication auth) {
        return clientService.getByAuth(auth);
        }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("First name is missing", HttpStatus.FORBIDDEN);
            }

        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Last name is missing", HttpStatus.FORBIDDEN);
            }

        if (email.isEmpty()) {
            return new ResponseEntity<>("Email is missing", HttpStatus.FORBIDDEN);
            }

        if (password.isEmpty()) {
            return new ResponseEntity<>("Password is missing", HttpStatus.FORBIDDEN);
            }

        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
            }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), RoleType.CLIENT);

        clientService.saveClient(client);

        Account account = createAccountForNewClient(client);

        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);

        }

        private Account createAccountForNewClient(Client client) {

            String number = "VIN-" + generateRandomAccountNumber();

            if (accountService.getAccountByNumber(number) != null){
                number = "VIN-" + generateRandomAccountNumber();
            }

            Account account = new Account(number, LocalDate.now(),0.0);
            account.setHolder(client);

            return account;
        }

        private String generateRandomAccountNumber() {
            Random rand = new Random();
            int randomNumber = rand.nextInt(900000) + 100000;
            return String.valueOf(randomNumber);
        }



}
