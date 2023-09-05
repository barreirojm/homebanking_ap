package com.ap.homebanking.services;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    Account getAccountByNumber(String number);

    void saveAccount(Account account);

    List<AccountDTO> getAccounts();

    ResponseEntity<AccountDTO> getAccount(@PathVariable Long id);

    List<AccountDTO> getAccount (Authentication auth);

    ResponseEntity<Object> createAccount (Authentication auth);


}
