package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.enums.user.FamilyRole;
import com.czabala.myhome.domain.model.enums.user.Fee;
import com.czabala.myhome.domain.model.enums.user.Newsletter;
import com.czabala.myhome.domain.model.enums.user.PaymentMethod;
import com.czabala.myhome.util.security.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String modules;
    private String token;
    private boolean confirmed;
    private Timestamp tokenExpirationDate;
    private Fee fee;
    private int monthliesAmount;
    private int monthliesPaid;
    private PaymentMethod paymentMethod;
    private String paymentDetails;
    private String paymentToken;
    private FamilyRole familyRole;
    private String address;
    private Date birthDate;
    private String phoneNumber;
    private String avatar;
    private Newsletter newsletter;
    private FamilyGroup familyGroup;


    public void registerUser() {
        setName("0");
        setSurname("0");
        if (role == null || role.isEmpty()) {
            setRole("USER");
        }
        setPassword(new BCryptPasswordEncoder().encode(password));
        setModules("0");
        setToken(TokenGenerator.generateToken());
        setConfirmed(false);
        setTokenExpirationDate(TokenGenerator.generateExpirationDate());
        setFee(Fee.FREE);
        setMonthliesAmount(0);
        setMonthliesPaid(0);
        setPaymentMethod(PaymentMethod.NONE);
        setPaymentDetails("");
        setPaymentToken("");
        setFamilyRole(FamilyRole.NONE);
        setAddress("");
        setBirthDate(Date.valueOf(LocalDate.of(1900, 1, 1)));
        setPhoneNumber("");
        setAvatar("");
        setNewsletter(Newsletter.NONE);
        setFamilyGroup(null);
    }
}