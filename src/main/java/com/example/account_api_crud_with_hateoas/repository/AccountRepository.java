package com.example.account_api_crud_with_hateoas.repository;

import com.example.account_api_crud_with_hateoas.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("UPDATE Account a SET a.balance = a.balance +?1 WHERE a.id = ?2 ")
    @Modifying
    public void updateBalance(float amount, Integer id);
}
