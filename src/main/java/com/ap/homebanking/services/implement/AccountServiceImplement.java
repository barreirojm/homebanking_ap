package com.ap.homebanking.services.implement;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<AccountDTO> getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<AccountDTO> getAccount(Authentication auth) {

        Client client  = clientRepository.findByEmail(auth.getName());

        return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> createAccount(Authentication auth) {
        return null;
    }

    @Override
    public void deleteAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setActive(false);
            accountRepository.save(account);
        }
    }


}
