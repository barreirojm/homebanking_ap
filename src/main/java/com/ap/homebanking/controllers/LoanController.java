package com.ap.homebanking.controllers;
import com.ap.homebanking.dtos.LoanApplicationDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {

        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication auth) {

        Client client = clientRepository.findByEmail(auth.getName());

        ////////////////////////////////////////

        if (loanApplicationDTO.getLoanId() == null) {
            return new ResponseEntity<>("Loan ID is required.", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Loan amount is required.", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Number of payments is required.", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getToAccountNumber() == null || loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Missing destination account number", HttpStatus.FORBIDDEN);
        }

        ////////////////////////////////////////

        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        if (loan == null) {
            return new ResponseEntity<>("Loan not available", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Loan amount exceeds maximum allowed", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Invalid number of payments", HttpStatus.FORBIDDEN);
        }

        ////////////////////////////////////////

        Account destinationAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

        if (destinationAccount == null) {
            return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
        }

        if (!destinationAccount.getHolder().equals(client)) {
            return new ResponseEntity<>("Destination account does not belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }

        ////////////////////////////////////////

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments());

        loan.addClientLoan(clientLoan);
        client.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);

        ////////////////////////////////////////

        Transaction transaction = new Transaction(TransactionType.CREDIT, clientLoan.getAmount(),
                loan.getName() + " loan approved", LocalDateTime.now());

        transaction.setAccount(destinationAccount);

        transactionRepository.save(transaction);

        ////////////////////////////////////////

        destinationAccount.setBalance(destinationAccount.getBalance() + clientLoan.getAmount());
        accountRepository.save(destinationAccount);

        ////////////////////////////////////////


        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);


    }




}
