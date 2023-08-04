package com.ap.homebanking.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Client {

    // Atributos de la clase Client
    // @Id indicara cuál será la clave primaria de nuestra clase.
    // @Entity indica a Spring que nos genere una tabla en la base de datos.
    // @GeneratedValue
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String mail;

    // Constructor vacio por defecto sirve para SpringBoot (siempre se usa)
    public Client(){
    }

    // Constructor full con los parametros
    public Client(String firstName, String lastName, String mail){
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    // Métodos accesores getters y setters
    public Integer getId() {
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
