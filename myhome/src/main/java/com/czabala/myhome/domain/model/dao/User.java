package com.czabala.myhome.domain.model.dao;

import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.util.security.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Date birthDate;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private boolean isConfirmed;
    @Column(nullable = false)
    private Timestamp registerDate;
    @Column
    private Timestamp lastLoginDate;
    @Enumerated(EnumType.STRING)
    @Column
    private FamilyRole familyRole;
    @Column(nullable = false)
    private boolean newsletter;
    @ManyToOne
    private Family family;
    @Column(nullable = false)
    private boolean isEnabled;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public void initializeUser() {
        this.role = Role.USER;
        this.password = new BCryptPasswordEncoder().encode(this.password);
        this.isConfirmed = false;
        this.registerDate = Timestamp.valueOf(LocalDateTime.now());
        this.isEnabled = true;
    }

    public UserDTO toDTO() {
        this.password = null;
        return new ModelMapper().map(this, UserDTO.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public enum FamilyRole {
        HEAD,
        MEMBER
    }
}