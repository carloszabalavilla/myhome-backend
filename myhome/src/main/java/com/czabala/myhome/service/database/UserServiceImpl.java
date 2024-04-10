package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.UserRole;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.EmailService;
import com.czabala.myhome.util.exception.TokenValidationException;
import com.czabala.myhome.util.exception.UnableToDeleteResource;
import com.czabala.myhome.util.security.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public Set<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (NullPointerException e) {
            return Collections.emptySet();
        }
    }

    @Override
    public User findById(long id) {
        if (userRepository.findById(id) == null) {
            User user = new User();
            user.setId(0);
            return user;
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<User> findByRole(UserRole role) {
        return userRepository.findByUserRole(role);
    }

    @Override
    public User add(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }
        registerUser(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        try {
            userRepository.findById(id);
            userRepository.deleteById(id);
        } catch (NullPointerException ignored) {
            throw new UnableToDeleteResource("Error al eliminar usuario: ID no válido");
        }
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUserRole(userDTO.getRole());
        user.setModules(userDTO.getModules());
        return user;
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
