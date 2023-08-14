package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    // Atributos de la clase Client
    // @Id indicara cuál será la clave primaria de nuestra clase.
    // @Entity indica a Spring que nos genere una tabla en la base de datos.
    // @GeneratedValue The annotation
    // may be applied to a primary key property or field of an entity or mapped
    // superclass in conjunction with the Id annotation.
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy="holder", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account) {
        account.setHolder(this);
        accounts.add(account);
    }
    ///////////////////////////
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientloans = new HashSet<>();

    public Set<ClientLoan> getLoans() {
        return clientloans;
    }

    public void addClientLoan(ClientLoan clientloan) {
        clientloan.setClient(this);
        clientloans.add(clientloan);
    }
    //////////////////////////

    // Constructor vacio por defecto sirve para SpringBoot (siempre se usa)
    public Client(){
    }

    // Constructor full con los parametros
    public Client(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Métodos accesores getters y setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /////
    /*public List<Loan> getLoans() {
        List<Loan> loans = new ArrayList<>();
        for (ClientLoan clientloan : clientloans) {
            loans.add(clientloan.getLoan());
        }
        return loans;
    }*/

}
