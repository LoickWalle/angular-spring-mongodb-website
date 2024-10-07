package com.example.YummyFridgeBack.entities;

import com.example.YummyFridgeBack.utils.enums.Allergy;
import com.example.YummyFridgeBack.utils.enums.Diet;
import com.example.YummyFridgeBack.utils.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public class Account {
    @Id
    private UUID id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    @NotBlank
    @Indexed(unique = true)
    private String email;
    @NotBlank
    private String password;
    private LocalDate birthdate;
    @Builder.Default
    private List<Allergy> allergies= new ArrayList<>();
    private Diet diet;
    private Role role;
}
