package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.ClientLoan;
import com.ap.homebanking.models.Loan;

public class ClientLoanDTO {

    /*
    * Debes crear un nuevo ClientLoanDTO que contenga el id del ClientLoan,
    * el id del préstamo, el nombre, el monto solicitado y las cuotas a pagar
    * (el monto y las cuotas deben obtenerse de ClientLoan ya que si se obtienen de Loan entonces se vería el montoMaximo y no el que solicitó el cliente,
    * de igual manera con los pagos)
    * el constructor de ClientLoanDTO recibirá un objeto ClientLoan del cual se obtendrán los valores.
    * */

    private Long id; // id propio de ClientLoanDTO
    private Long clientLoanId; // id del ClientLoan
    private Long loanId; // id del préstamo
    private String name; // Nombre del préstamo
    private double amount; // Monto solicitado por el cliente
    private int payments; // Cuotas a pagas elegidas por el cliente
    private ClientLoan clientLoan;
    /*private Client client;
    private Loan loan;*/


    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.clientLoanId = clientLoan.getId();
        this.loanId = (clientLoan.getLoan()).getId();
        this.name = (clientLoan.getLoan()).getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        /*this.client = client;
        this.loan = loan;*/
    }

    /*public ClientLoanDTO(ClientLoan clientLoan) {
    }*/

    public Long getId() {
        return id;
    }

    public Long getClientLoanId() {
        return clientLoanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() { return amount; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    /*public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }*/

    public ClientLoan getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(ClientLoan clientLoan) {
        this.clientLoan = clientLoan;
    }
}

