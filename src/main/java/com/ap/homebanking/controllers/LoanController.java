package com.ap.homebanking.controllers;
import com.ap.homebanking.dtos.LoanApplicationDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.ClientLoan;
import com.ap.homebanking.models.Loan;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.LoanRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {

        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }


    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication auth) {

        ////////////////////////////////////////

        if (loanApplicationDTO.getLoanId() == null) {
            return new ResponseEntity<>("Loan ID is required.", HttpStatus.FORBIDDEN);;
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


        Client client = clientRepository.findByEmail(auth.getName());

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

        // Crear la solicitud de préstamo
        /*LoanApplicationDTO loanApplicationDTO = new LoanApplication();
        loanApplication.setLoan(loan);
        loanApplication.setAmount(loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.20)); // Sumar el 20%
        loanApplication.setClient(client);

        loanApplication = loanApplicationRepository.save(loanApplication);*/

        ClientLoan clientLoan = new ClientLoan(**, loanApplicationDTO.getLoanId(), loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments() )

        ////////////////////////////////////////

        // Crear la transacción "CREDIT"
        Transaction transaction = new Transaction();
        transaction.setAmount(loanApplication.getAmount());
        transaction.setType(TransactionType.CREDIT);
        transaction.setDescription(loan.getName() + " loan approved");
        transaction.setAccount(destinationAccount);

        transactionRepository.save(transaction);

        ////////////////////////////////////////

        // Actualizar el saldo de la cuenta de destino
        destinationAccount.setBalance(destinationAccount.getBalance() + loanApplication.getAmount());
        accountRepository.save(destinationAccount);

        ////////////////////////////////////////

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
