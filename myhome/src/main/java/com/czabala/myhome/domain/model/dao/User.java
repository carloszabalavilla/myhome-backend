package com.czabala.myhome.domain.model.dao;

import com.czabala.myhome.domain.model.enums.user.FamilyRole;
import com.czabala.myhome.domain.model.enums.user.Fee;
import com.czabala.myhome.domain.model.enums.user.Newsletter;
import com.czabala.myhome.domain.model.enums.user.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column
    private String modules;
    @Column
    private String token;
    @Column(nullable = false)
    private boolean isConfirmed;
    @Column
    private Timestamp tokenExpirationDate;
    @Column(nullable = false)
    private Fee fee;
    @Column
    private int monthliesAmount;
    @Column
    private int monthliesPaid;
    @Column
    private PaymentMethod paymentMethod;
    @Column
    private String paymentDetails;
    @Column
    private String paymentToken;
    @Column
    private FamilyRole familyRole;
    @Column
    private String address;
    @Column(nullable = false)
    private Date birthDate;
    @Column
    private String phoneNumber;
    @Column
    private String avatar;
    @Column(nullable = false)
    private Newsletter newsletter;
    @ManyToOne
    private FamilyGroup familyGroup;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public boolean validateToken(String token) {
        return this.token.equals(token);
    }

    public boolean isTokenExpired() {
        return !tokenExpirationDate.after(Timestamp.valueOf(LocalDateTime.now()));
    }

    public boolean isPasswordValid(String password) {
        return this.password.equals(password);
    }

    public boolean isEmailValid(String email) {
        return this.email.equals(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + role));
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
    //TODO: implement enabled field instead of deleting the user from db
    @Override
    public boolean isEnabled() {
        return true;
    }
}