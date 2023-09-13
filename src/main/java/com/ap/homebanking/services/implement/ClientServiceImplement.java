package com.ap.homebanking.services.implement;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        }

    @Override
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

    @Override
    public ClientDTO getByAuth(Authentication auth){
        Client client  = clientRepository.findByEmail(auth.getName());
        ClientDTO clientDTO = new ClientDTO(client);
        return clientDTO;
    }

    @Override
    public Client getClientByAuth(Authentication auth){
        return clientRepository.findByEmail(auth.getName());
    }

    @Override
    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {
        return null;
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }


}
