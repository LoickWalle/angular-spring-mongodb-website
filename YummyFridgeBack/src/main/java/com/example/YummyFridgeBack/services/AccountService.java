package com.example.YummyFridgeBack.services;

import com.example.YummyFridgeBack.entities.Account;
import com.example.YummyFridgeBack.exceptions.ConflictException;
import com.example.YummyFridgeBack.exceptions.NotFoundException;
import com.example.YummyFridgeBack.interfaces.IAccountService;
import com.example.YummyFridgeBack.repositories.AccountRepository;
import com.example.YummyFridgeBack.utils.constants.ExceptionsMessages;
import com.example.YummyFridgeBack.utils.enums.Role;
import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AccountService implements IAccountService {

    @Autowired
    private MessageSource messageSource;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account accountToSave) {
        log.info("saveAccount:: save account with id {} and email {}", accountToSave.getId(), accountToSave.getEmail());

        if(accountToSave.getId()==null){
            accountToSave.setRole(Role.USER);
        } else {
            if(this.accountRepository.findById(accountToSave.getId()).isEmpty())
                throw new NotFoundException(
                        messageSource.getMessage("exception.account.to.update.not.found", null, LocaleContextHolder.getLocale()),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                );
        }

        //accountToCreate.setPassword(HashingUtils.hashPassword(accountToCreate.getPassword()));

        try {
            return this.accountRepository.save(accountToSave);
        } catch (DuplicateKeyException e) {
            throw new ConflictException(
                    messageSource.getMessage("exception.account.to.save.email.already.exist", null, LocaleContextHolder.getLocale()),
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
        }
    }

    @Override
    public List<Account> getAccounts() {
        log.info("getAccounts:: get all accounts");
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID accountId) {
        log.info("getAccountById:: get account with id {}", accountId);
        return this.accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Account deleteAccount(Account accountToDelete) {
        log.info("deleteAccount:: delete account with id {}", accountToDelete.getId());

        Account accountFoundToDelete = this.accountRepository.findById(accountToDelete.getId()).orElse(null);
        if(accountFoundToDelete == null)
            throw new NotFoundException(
                    messageSource.getMessage("exception.account.to.delete.not.found", null, LocaleContextHolder.getLocale()),
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );

        this.accountRepository.delete(accountFoundToDelete);
        return accountFoundToDelete;
    }
}
