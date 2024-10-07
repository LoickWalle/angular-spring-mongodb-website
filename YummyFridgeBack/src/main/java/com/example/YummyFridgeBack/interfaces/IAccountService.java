package com.example.YummyFridgeBack.interfaces;

import com.example.YummyFridgeBack.entities.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    Account saveAccount(Account accountToCreate);
    List<Account> getAccounts();
    Account getAccountById(UUID accountId);
    Account deleteAccount(Account accountToDelete);
}
