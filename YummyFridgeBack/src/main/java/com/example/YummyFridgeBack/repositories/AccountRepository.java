package com.example.YummyFridgeBack.repositories;

import com.example.YummyFridgeBack.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, UUID> {
    Account findByEmail(String email);
}