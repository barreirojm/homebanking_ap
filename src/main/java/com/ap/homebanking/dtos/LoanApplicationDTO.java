package com.ap.homebanking.dtos;

public class LoanApplicationDTO {

    private Long loanId;
    private double amount;
    private int payments;
    private String toAccountNumber;

    public Long getLoanId() { return loanId; }

    public double getAmount() { return amount; }

    public int getPayments() { return payments; }

    public String getToAccountNumber() { return toAccountNumber; }

}
