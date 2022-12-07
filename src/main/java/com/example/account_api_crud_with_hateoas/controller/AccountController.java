package com.example.account_api_crud_with_hateoas.controller;

import com.example.account_api_crud_with_hateoas.Amount;
import com.example.account_api_crud_with_hateoas.model.Account;
import com.example.account_api_crud_with_hateoas.service.AccountService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Account>> listAll() {
        List<Account> listAccounts = service.listAll();

        if(listAccounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for(Account account : listAccounts) {
            account.add(linkTo(methodOn(AccountController.class).getOne(account.getId())).withSelfRel());
            account.add(linkTo(methodOn(AccountController.class).deposit(account.getId(), null)).withRel("deposits"));
            account.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
        }

        CollectionModel<Account> model = CollectionModel.of(listAccounts);
        model.add(linkTo(methodOn(AccountController.class).listAll()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getOne(@PathVariable("id") Integer id) {
        try {
            Account account = service.get(id);
            account.add(linkTo(methodOn(AccountController.class).getOne(account.getId())).withSelfRel());
            account.add(linkTo(methodOn(AccountController.class).deposit(account.getId(), null)).withRel("deposits"));
            account.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

            return new ResponseEntity<>(account, HttpStatus.OK);

        } catch(NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Account> add(@RequestBody Account account) {
        Account savedAccount = service.save(account);

        savedAccount.add(linkTo(methodOn(AccountController.class).getOne(savedAccount.getId())).withSelfRel());
        savedAccount.add(linkTo(methodOn(AccountController.class).deposit(savedAccount.getId(), null)).withRel("deposits"));
        savedAccount.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(
                linkTo(methodOn(AccountController.class).getOne(savedAccount.getId())).toUri()).body(savedAccount);
    }

    @PutMapping
    public ResponseEntity<Account> replace(@RequestBody Account account) {
        Account updatedAccount = service.save(account);

        updatedAccount.add(linkTo(methodOn(AccountController.class).getOne(updatedAccount.getId())).withSelfRel());
        updatedAccount.add(linkTo(methodOn(AccountController.class).deposit(updatedAccount.getId(), null)).withRel("deposits"));
        updatedAccount.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        Account updatedAccount = service.deposit(amount.getAmount(), id);

        updatedAccount.add(linkTo(methodOn(AccountController.class).getOne(updatedAccount.getId())).withSelfRel());
        updatedAccount.add(linkTo(methodOn(AccountController.class).deposit(updatedAccount.getId(), null)).withRel("deposits"));
        updatedAccount.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}
