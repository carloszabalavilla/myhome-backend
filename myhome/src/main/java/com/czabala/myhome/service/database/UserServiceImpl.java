package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.user.UserRole;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.EmailService;
import com.czabala.myhome.util.exception.AuthErrorException;
import com.czabala.myhome.util.exception.InvalidEmailException;
import com.czabala.myhome.util.exception.TokenValidationException;
import com.czabala.myhome.util.exception.UserNotFoundException;
import com.czabala.myhome.util.mapper.MapperDTOtoDAO;
import com.czabala.myhome.util.security.TokenGenerator;
import com.czabala.myhome.util.validator.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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
    public User findByToken(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        return user;
    }

    @Override
    public User add(UserDTO userDTO) {
        if (findByEmail(userDTO.getEmail()) != null) {
            throw new InvalidEmailException("Error al añadir usuario: Email ya registrado");
        }
        userDTO.registerUser();
        User newUser = modelMapper().map(userDTO, User.class);
        startConfirmationProcess(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User update(UserDTO userDTO) {
        if (findById(userDTO.getId()) == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        User user = modelMapper().map(userDTO, User.class);
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
        } /*else if (!user.isConfirmed()) {
            throw new TokenValidationException("Usuario no confirmado");
        }
        */
        user.setToken(TokenGenerator.getJWTToken(user.getEmail()));
        return user;
    }

    public void recoveryPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        String token = TokenGenerator.generateToken();
        user.setToken(token);
        user.setTokenExpirationDate(TokenGenerator.generateExpirationDate());
        emailService.sendRecoveryMessage(user.getEmail(), token);
    }

    @Override
    public void changePassword(UserDTO userDTO) {
        User user = userRepository.findByToken(userDTO.getToken());
        if (user == null || !user.isTokenValid(userDTO.getToken()) || user.isTokenExpired()) {
            throw new TokenValidationException("Token de confirmación no válido o expirado");
        }
        user.setPassword(userDTO.getPassword());
        user.setToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }

    private void startConfirmationProcess(User user) {
        String token = TokenGenerator.generateToken();
        user.setToken(token);
        user.setTokenExpirationDate(TokenGenerator.generateExpirationDate());
        emailService.sendConfirmationMessage(user.getEmail(), token);
    }

    public void processConfirmationToken(String token) {
        User user = userRepository.findByToken(token);
        if (user == null || !user.isTokenValid(token) || user.isTokenExpired()) {
            throw new TokenValidationException("Token de confirmación no válido o expirado");
        }
        user.setConfirmed(true);
        user.setToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }
}
