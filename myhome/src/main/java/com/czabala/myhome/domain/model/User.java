package com.czabala.myhome.domain.model;

import com.czabala.myhome.domain.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
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
    private UserRole userRole;
    @Column
    private String modules;
    @Column(nullable = false)
    private String confirmationToken;
    @Column(nullable = false)
    private boolean confirmed;
    @Column(nullable = false)
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


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != -1 && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public boolean isTokenValid(String token) {
        return token.equals(confirmationToken);
    }

    public boolean isTokenNotExpired() {
        return tokenExpirationDate.after(Timestamp.valueOf(LocalDateTime.now()));
    }

    public boolean hasNotNullOrEmpty() {
        return name == null && surname == null && email == null && password == null && userRole == null &&
                name.isEmpty() && surname.isEmpty() && email.isEmpty() && password.isEmpty();
    }
}