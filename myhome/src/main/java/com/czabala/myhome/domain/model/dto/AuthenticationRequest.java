package com.czabala.myhome.domain.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * Email must be in a valid email format.
     */
    @NotNull
//    @Size(min = 5, max = 40)
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

    /**
     * Password must contain at least one digit, one lowercase letter, one uppercase letter and be at least 8 characters long.
     */
    @NotNull
//    @Size(min = 8, max = 40)
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,}$")
    private String password;

}
