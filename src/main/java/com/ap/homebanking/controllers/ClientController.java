package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.RoleType;
import com.ap.homebanking.repositories.ClientRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){

        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());

        }

    @RequestMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

   @RequestMapping("/clients/current")
    public ClientDTO getByAuth(Authentication auth){
        Client client  = clientRepository.findByEmail(auth.getName());
        ClientDTO clientDTO = new ClientDTO(client);
        return clientDTO;
        }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
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

        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

            }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password), RoleType.CLIENT));

        return new ResponseEntity<>(HttpStatus.CREATED);

        }

}
