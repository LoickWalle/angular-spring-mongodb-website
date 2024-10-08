package com.example.YummyFridgeBack.Integration;

import com.example.YummyFridgeBack.entities.Account;
import com.example.YummyFridgeBack.repositories.AccountRepository;
import com.example.YummyFridgeBack.utils.constants.ApiUrls;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // Use full Spring context to test MongoDB integration
@AutoConfigureMockMvc // Enable MockMvc for controller testing
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;  // Inject MongoTemplate to work with MongoDB

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        accountRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Clean up database after each test if necessary
        accountRepository.deleteAll();
    }

    @DisplayName("Test account creation")
    @Test
    void testCreateAccount() throws Exception {
        // Create a sample account
        Account account = new Account();
        account.setFirstname("John");
        account.setLastname("Doe");
        account.setEmail("john.doe@example.com");
        account.setPassword("securePassword");

        // Perform the POST request to create the account
        mockMvc.perform(post(ApiUrls.API+ApiUrls.ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @DisplayName("Test fetching account by ID")
    @Test
    void testGetAccountById() throws Exception {
        // Create and save an account
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setFirstname("Jane");
        account.setLastname("Smith");
        account.setEmail("jane.smith@example.com");
        account.setPassword("securePassword");

        // Save directly to the repository
        Account savedAccount = accountRepository.save(account);

        // Perform the GET request to fetch the account
        mockMvc.perform(get(ApiUrls.API+ApiUrls.ACCOUNT +"/"+ savedAccount.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }
}
