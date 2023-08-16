package com.ap.homebanking.dtos;

import com.ap.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id; // id del ClientLoan
    private Long loanId; // id del préstamo
    private String name; // Nombre del préstamo
    private double amount; // Monto solicitado por el cliente
    private int payments; // Cuotas a pagas elegidas por el cliente


    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = (clientLoan.getLoan()).getId();
        this.name = (clientLoan.getLoan()).getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();

    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() { return amount; }

    public int getPayments() {
        return payments;
    }


}

