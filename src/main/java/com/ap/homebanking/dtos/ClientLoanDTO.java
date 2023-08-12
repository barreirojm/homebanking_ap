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
    private Long id_clientLoan; // id del ClientLoan
    private Long id_loan; // id del préstamo
    private String loanName; // Nombre del préstamo
    private double loanAmountRequested; // Monto solicitado por el cliente
    private int paymentsRequested; // Cuotas a pagas elegidas por el cliente
    private ClientLoan clientLoan;
    /*private Client client;
    private Loan loan;*/


    public ClientLoanDTO() {
    }

    public ClientLoanDTO(Long id_clientLoan, Long id_loan, String loanName, double loanAmountRequested, int paymentsRequested,ClientLoan clientLoan /*,Client client, Loan loan*/) {
        this.id_clientLoan = clientLoan.getId();
        this.id_loan = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.loanAmountRequested = clientLoan.getAmount();
        this.paymentsRequested = clientLoan.getPayments();
        /*this.client = client;
        this.loan = loan;*/
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
    }

    public Long getId() {
        return id;
    }

    public Long getId_clientLoan() {
        return id_clientLoan;
    }

    public Long getId_loan() {
        return id_loan;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public double getLoanAmountRequested() {
        return loanAmountRequested;
    }

    public void setLoanAmountRequested(double loanAmountRequested) {
        this.loanAmountRequested = loanAmountRequested;
    }

    public int getPaymentsRequested() {
        return paymentsRequested;
    }

    public void setPaymentsRequested(int paymentsRequested) {
        this.paymentsRequested = paymentsRequested;
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

