package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.UserRole;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.EmailService;
import com.czabala.myhome.util.exception.InvalidEmailException;
import com.czabala.myhome.util.exception.TokenValidationException;
import com.czabala.myhome.util.security.TokenGenerator;
import com.czabala.myhome.util.user.UserMapper;
import com.czabala.myhome.util.validator.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenGenerator = new TokenGenerator();
    }

    @Override
    public Set<User> findAll() throws NullPointerException {
        if (userRepository.findAll().isEmpty()) {
            throw new NullPointerException("No hay usuarios registrados");
        }
            return userRepository.findAll();
    }

    @Override
    public User findById(long id) throws NullPointerException {
        if (userRepository.findById(id) == null) {
            throw new NullPointerException("Usuario no encontrado");
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) throws NullPointerException, InvalidEmailException {
        EmailValidator.validateEmail(email);
        if (userRepository.findByEmail(email) == null) {
            throw new NullPointerException("Usuario no encontrado");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<User> findByRole(String role) throws NullPointerException {
        UserRole userRole = UserRole.valueOf(role);
        Set<User> users;
        if ((users = userRepository.findByUserRole(userRole)) == null || users.isEmpty()) {
            throw new NullPointerException("No hay usuarios con el rol " + role);
        }
        return users;
    }

    @Override
    public User add(UserDTO userDTO) throws IllegalAccessException {
        User user = new User();
        UserMapper.copyNonNullFields(userDTO, user);
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Error al añadir usuario: Email ya registrado");
        }
        //registerUser(user);
        return userRepository.save(user);
    }

    @Override
    public User update(UserDTO userDTO) throws IllegalAccessException, NullPointerException {
        User user = findById(userDTO.getId());
        UserMapper.copyNonNullFields(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) throws NullPointerException {
            userRepository.findById(id);
            userRepository.deleteById(id);
    }

    private void registerUser(User user) {
        String token = tokenGenerator.generateToken();
        user.setConfirmationToken(token);
        user.setTokenExpirationDate(tokenGenerator.generateExpirationDate());
        emailService.sendConfirmationMessage(user.getEmail(), token);
    }

    public void processConfirmationToken(String token) {
        User user = userRepository.findByConfirmationToken(token);

        if (user != null && user.isTokenValid(token) && user.isTokenNotExpired()) {
            user.setConfirmed(true);
            userRepository.save(user);
        } else {
            throw new TokenValidationException("Token de confirmación no válido o expirado");
        }
    }
}
