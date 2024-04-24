package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.user.UserRole;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.EmailService;
import com.czabala.myhome.util.exception.*;
import com.czabala.myhome.util.security.TokenGenerator;
import com.czabala.myhome.util.user.MapperDTOtoDAO;
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
    public Set<User> findAll() {
        Set<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No hay usuarios registrados");
        }
        return users;
    }


    @Override
    public User findById(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        EmailValidator.validateEmail(email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<User> findByRole(String role) {
        UserRole userRole = UserRole.valueOf(role);
        Set<User> users = userRepository.findByUserRole(userRole);
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("No hay usuarios con el rol " + role + " registrados.");
        }
        return users;
    }

    @Override
    public User add(UserDTO userDTO) {
        User user = new User();
        try {
            MapperDTOtoDAO.copyNonNullFields(userDTO, user);
            findByEmail(user.getEmail());
            throw new InvalidEmailException("Error al añadir usuario: Email ya registrado");
        } catch (UserNotFoundException e) {
            //registerUser(user)
            return userRepository.save(user);
        } catch (ApiException e) {
            throw new ApiException("Error al añadir usuario: " + e.getMessage());
        }
    }

    @Override
    public User update(UserDTO userDTO) {
        User user = findById(userDTO.getId());
        MapperDTOtoDAO.copyNonNullFields(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        if (userRepository.findById(id) == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        userRepository.deleteById(id);
    }

    public User login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado o contraseña incorrecta");
        } else if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new AuthErrorException("Contraseña incorrecta");
        } else if (!user.isConfirmed()) {
            throw new TokenValidationException("Usuario no confirmado");
        }
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
