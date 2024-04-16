package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.FamilyGroup;
import com.czabala.myhome.domain.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserRole userRole;
    private String modules;
    private String confirmationToken;
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
}
