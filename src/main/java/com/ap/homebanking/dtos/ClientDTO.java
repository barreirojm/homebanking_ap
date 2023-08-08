package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Client;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
