package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.util.security.Role;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    @NotNull
    @Size(min = 5, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$")
    private String email;
    @NotNull
    @Size(min = 8, max = 40)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,}$")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Date birthDate;
    private Role role;
    private String address;
    private String phoneNumber;
    private boolean newsletter;
    private Long familyGroup;

    public User toUser() {
        this.email = this.email.toLowerCase();
        this.firstName = this.firstName.toLowerCase();
        this.lastName = this.lastName.toLowerCase();
        if (this.address != null)
            this.address = this.address.toLowerCase();
        return new ModelMapper().map(this, User.class);
    }
}
