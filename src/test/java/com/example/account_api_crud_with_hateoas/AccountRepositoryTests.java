package com.example.account_api_crud_with_hateoas;

import com.example.account_api_crud_with_hateoas.model.Account;
import com.example.account_api_crud_with_hateoas.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository repo;

    @Test
    public void testAccount() {
        Account account = new Account("0059426628", 200);
        Account savedAccount = repo.save(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isGreaterThan(0);
    }
}
