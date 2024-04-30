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
    public User findByToken(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
        return user;
    }

    @Override
    public User add(UserDTO userDTO) {
        User user = findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new InvalidEmailException("Error al añadir usuario: Email ya registrado");
        }
        user = new User();
        userDTO.registerUser();
        MapperDTOtoDAO.copyNonNullFields(userDTO, user);
        registerUser(user);
        return userRepository.save(user);
    }

    @Override
    public User update(UserDTO userDTO) {
        User user = findById(userDTO.getId());
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
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

    public void recoveryPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        String token = tokenGenerator.generateToken();
        user.setToken(token);
        user.setTokenExpirationDate(tokenGenerator.generateExpirationDate());
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

    private void registerUser(User user) {
        String token = tokenGenerator.generateToken();
        user.setToken(token);
        user.setTokenExpirationDate(tokenGenerator.generateExpirationDate());
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
