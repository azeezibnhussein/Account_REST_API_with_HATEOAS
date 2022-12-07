package com.example.account_api_crud_with_hateoas.configuration;

import com.example.account_api_crud_with_hateoas.model.Account;
import com.example.account_api_crud_with_hateoas.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AccountConfig {
    private AccountRepository repo;

    public AccountConfig(AccountRepository repo) {
        this.repo = repo;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            Account account1 = new Account("1234567890", 100);
            Account account2 = new Account("0123456789", 100);
            Account account3 = new Account("2345678901", 100);

            repo.saveAll(List.of(account1, account2, account3));
            System.out.println("Sample database Initialised successfully");
        };
    }
}
