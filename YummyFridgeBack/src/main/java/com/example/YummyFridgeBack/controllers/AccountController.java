package com.example.YummyFridgeBack.controllers;

import com.example.YummyFridgeBack.entities.Account;
import com.example.YummyFridgeBack.interfaces.IAccountService;
import com.example.YummyFridgeBack.utils.constants.ApiUrls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiUrls.API+ApiUrls.ACCOUNT)
public class AccountController {
    private final IAccountService iAccountService;

    public AccountController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(){
        return new ResponseEntity<>(this.iAccountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccounts(@PathVariable @NotBlank String id){
        return new ResponseEntity<>(this.iAccountService.getAccountById(UUID.fromString(id)), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Account> saveAccount(@Valid @RequestBody Account accountToSave){
        return new ResponseEntity<>(this.iAccountService.saveAccount(accountToSave), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Account> deleteAccount(@Valid @RequestBody Account accountToDelete){
        return new ResponseEntity<>(this.iAccountService.deleteAccount(accountToDelete), HttpStatus.OK);
    }
}
