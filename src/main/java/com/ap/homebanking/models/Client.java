package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy="holder", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientloans = new HashSet<>();
    @OneToMany(mappedBy="card_holder", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client(){
    }

    public Client(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<ClientLoan> getLoans() {
        return clientloans;
    }

    public Set<Card> getCards() { return cards; }

    public void addAccount(Account account) {
        account.setHolder(this);
        accounts.add(account);
    }
    public void addClientLoan(ClientLoan clientloan) {
        clientloan.setClient(this);
        clientloans.add(clientloan);
    }
    public void addCard(Card card) {
        card.setCard_holder(this);
        cards.add(card);
    }


}
