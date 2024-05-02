package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.enums.user.*;
import com.czabala.myhome.util.security.PasswordEncryptor;
import com.czabala.myhome.util.security.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String salt;
    private UserRole userRole;
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
        setUserRole(UserRole.USER);
        String[] encryptedPassword = PasswordEncryptor.encryptPassword(getPassword(), null);
        setPassword(encryptedPassword[0]);
        setSalt(encryptedPassword[1]);
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