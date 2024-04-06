package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String modules;

    public UserDTO(String name, String email, String password, String role, String modules) {
        this.name = name;
        this.email = email;
        this.password = password;
        setRole(role);
        this.modules = modules;
    }

    public void setRole(String role) {
        if (role.equals("ADMIN")) {
            this.role = UserRole.ADMIN;
        } else {
            this.role = UserRole.USER;
        }
    }

    public boolean hasNotNullOrEmpty() {
        return name != null && email != null && password != null && role != null &&
                !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }
}
