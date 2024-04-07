package com.czabala.myhome.util.db;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void loadData() {
        String sqlScript = "INSERT INTO user (name, surname, email, password, user_role, modules, confirmation_token, confirmed, token_expiration_date, fee, monthlies_amount, monthlies_paid, payment_method, payment_details, payment_token, family_role, address, birth_date, phone_number, avatar, newsletter, family_group_id)\n" +
                "VALUES\n" +
                "('John', 'Doe', 'john@example.com', 'password123', 0, NULL, 'token123', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '123 Main Street', '1990-01-01', '1234567890', NULL, true, NULL),\n" +
                "('Alice', 'Smith', 'alice@example.com', 'password456', 0, NULL, 'token456', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '456 Oak Street', '1985-03-15', '9876543210', NULL, true, NULL),\n" +
                "('Emily', 'Johnson', 'emily@example.com', 'password789', 0, NULL, 'token789', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '789 Elm Street', '1988-07-22', '5551234567', NULL, true, NULL),\n" +
                "('Michael', 'Williams', 'michael@example.com', 'passwordabc', 0, NULL, 'tokenabc', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '456 Maple Street', '1992-09-10', '7779876543', NULL, true, NULL),\n" +
                "('Emma', 'Brown', 'emma@example.com', 'passworddef', 0, NULL, 'tokendef', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '789 Pine Street', '1995-12-05', '8883332221', NULL, true, NULL),\n" +
                "('William', 'Jones', 'william@example.com', 'passwordghi', 0, NULL, 'tokenghi', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '123 Cedar Street', '1983-04-18', '3334445556', NULL, true, NULL),\n" +
                "('Olivia', 'Garcia', 'olivia@example.com', 'passwordjkl', 0, NULL, 'tokenjkl', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '456 Birch Street', '1998-06-30', '6667778889', NULL, true, NULL),\n" +
                "('James', 'Rodriguez', 'james@example.com', 'passwordmno', 0, NULL, 'tokenmno', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '789 Oak Street', '1980-08-25', '2223334445', NULL, true, NULL),\n" +
                "('Sophia', 'Martinez', 'sophia@example.com', 'passwordpqr', 0, NULL, 'tokenpqr', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '123 Pine Street', '1987-10-12', '9991112223', NULL, true, NULL),\n" +
                "('Alexander', 'Hernandez', 'alexander@example.com', 'passwordstu', 0, NULL, 'tokenstu', true, CURRENT_TIMESTAMP, 0, 0, 0, NULL, NULL, NULL, NULL, '456 Elm Street', '1993-02-20', '4445556667', NULL, true, NULL);\n";
        jdbcTemplate.execute(sqlScript);
    }
}