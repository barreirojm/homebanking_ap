package com.ap.homebanking.services;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClients();

    ResponseEntity<ClientDTO> getClient(@PathVariable Long id);

    ClientDTO getByAuth(Authentication auth);

    Client getClientByEmail(String email);

    Client getClientByAuth(Authentication auth);

    ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password);

    void saveClient(Client client);



}
