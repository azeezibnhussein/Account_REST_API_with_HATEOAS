package com.example.account_api_crud_with_hateoas.service;

import com.example.account_api_crud_with_hateoas.model.Account;
import com.example.account_api_crud_with_hateoas.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> listAll() {
        return repo.findAll();
    }

    public Account get(Integer id) {
        return repo.findById(id).get();
    }

    public Account save(Account account) {
        return repo.save(account);
    }

    public Account deposit(float amount, Integer id) {
        repo.updateBalance(amount, id);
        return repo.findById(id).get();
    }


}
