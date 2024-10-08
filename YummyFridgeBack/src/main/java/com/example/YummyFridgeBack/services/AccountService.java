package com.example.YummyFridgeBack.services;

import com.example.YummyFridgeBack.entities.Account;
import com.example.YummyFridgeBack.exceptions.ConflictException;
import com.example.YummyFridgeBack.exceptions.NotFoundException;
import com.example.YummyFridgeBack.exceptions.WrongParameterException;
import com.example.YummyFridgeBack.interfaces.IAccountService;
import com.example.YummyFridgeBack.repositories.AccountRepository;
import com.example.YummyFridgeBack.utils.enums.Role;
import lombok.extern.slf4j.Slf4j;
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

    private final AccountRepository accountRepository;
    private final MessageSource messageSource;

    public AccountService(AccountRepository accountRepository, MessageSource messageSource) {
        this.accountRepository = accountRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Account saveAccount(Account accountToSave) {
        log.info("saveAccount:: Saving account with email {}", accountToSave.getEmail());

        if(accountToSave.getId()==null){
            setNewAccount(accountToSave);
        } else {
            if(!this.accountRepository.existsById(accountToSave.getId()))
                throw new NotFoundException(
                        messageSource.getMessage("exception.account.to.update.not.found", null, LocaleContextHolder.getLocale()),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                );
        }

        return this.accountRepository.save(accountToSave);
    }

    @Override
    public List<Account> getAccounts() {
        log.info("getAccounts:: Retrieving all accounts");
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID accountId) {
        log.info("getAccountById:: Retrieving account with id {}", accountId);
        return this.accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("exception.account.with.id.not.found", null, LocaleContextHolder.getLocale()),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                ));
    }

    @Override
    public Account deleteAccount(Account accountToDelete) {
        log.info("deleteAccount:: Deleting account with email {}", accountToDelete.getEmail());

        if(accountToDelete.getId() == null){
            log.warn("deleteAccount:: Invalid account deletion attempt - ID is null");
            throw new WrongParameterException(
                    messageSource.getMessage("exception.account.to.delete.id.is.null", null, LocaleContextHolder.getLocale()),
                    HttpStatus.BAD_REQUEST,
                    LocalDateTime.now()
            );
        }


        Account accountFoundToDelete = this.accountRepository.findById(accountToDelete.getId())
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("exception.account.to.delete.not.found", null, LocaleContextHolder.getLocale()),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                ));

        this.accountRepository.delete(accountFoundToDelete);
        return accountFoundToDelete;
    }

    private boolean checkEmail(String email) {
        log.info("checkMail::check email with email {}", email);
        return this.accountRepository.findByEmail(email) != null;
    }

    private void setNewAccount(Account accountToSave) {
        log.info("setNewAccount::set a new account with email {}", accountToSave.getEmail());

        if(!accountToSave.getEmail().isEmpty() && !checkEmail(accountToSave.getEmail())){
            accountToSave.setRole(Role.USER);
            accountToSave.setId(UUID.randomUUID());
            //accountToCreate.setPassword(HashingUtils.hashPassword(accountToCreate.getPassword()));
        }
        else {
            throw new ConflictException(
                    messageSource.getMessage("exception.account.to.save.email.already.exist", null, LocaleContextHolder.getLocale()),
                    HttpStatus.CONFLICT,
                    LocalDateTime.now()
            );
        }
    }
}
