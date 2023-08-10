package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    // Client tiene una relación de uno a muchos con Account,
    // esto quiere decir que un cliente puede tener muchas cuentas y que una cuenta pertenece a un solo cliente.
    //the @JoinColumn annotation says which column has the ID of the client.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="holder_id")
    private Client holder;

    public Account() {
    }
    public Account(String number, LocalDate creationDate, double balance){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public Client getHolder() {
        return holder;
    }
    public void setHolder(Client holder) {
        this.holder = holder;
    }


}
