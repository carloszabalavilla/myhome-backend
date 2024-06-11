package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser {
    private long id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Date birthDate;
    private String address;
    private String phoneNumber;
    private boolean newsletter;

    public User toUser() {
        return new ModelMapper().map(this, User.class);
    }
}
