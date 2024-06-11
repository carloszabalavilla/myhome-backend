package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.AuthenthicationRequest;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.util.exception.ConflictInDatabaseException;
import com.czabala.myhome.util.mail.EmailSender;
import com.czabala.myhome.util.security.Role;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private JwtService jwtService;

    @Override
    public Set<UserDTO> findAll() {
        return toUsersDTO(userRepository.findAll());
    }

    @Override
    public UserDTO findById(long id) {
        return userRepository.findById(id).orElseThrow().toDTO();
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow().toDTO();
    }

    @Override
    public Set<UserDTO> findByRole(String role) {
        return toUsersDTO(userRepository.findByRole(Role.valueOf(role)));
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ConflictInDatabaseException("El email ya está registrado");
        }
        User newUser = userDTO.toUser();
        newUser.initializeUser();
        startConfirmationProcess(newUser);
        return userRepository.save(newUser).toDTO();
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return userRepository.save(userDTO.toUser()).toDTO();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void startChangePassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        String token = jwtService.generateToken(user);
        emailSender.sendChangePasswordMessage(user.getEmail(), token);
    }

    @Override
    public UserDTO letChangePassword(String jwt) {
        return userRepository.findByEmail(jwtService.getEmailFromToken(jwt)).orElseThrow().toDTO();
    }

    @Override
    public UserDTO changePassword(String jwt, AuthenthicationRequest auth) {
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(jwt)).orElseThrow();
        if (!user.getEmail().equals(auth.getEmail())) {
            throw new JwtException("El email no coincide con el token");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(auth.getPassword()));
        return userRepository.save(user).toDTO();
    }

    private void startConfirmationProcess(User user) {
        String token = jwtService.generateToken(user);
        emailSender.sendConfirmationMessage(user.getEmail(), token);
    }

    @Override
    public void resendConfirmation(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user.isConfirmed()) {
            throw new ConflictInDatabaseException("Usuario ya confirmado");
        }
        startConfirmationProcess(user);
    }

    @Override
    public UserDTO confirmEmail(String jwt) {
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(jwt)).orElseThrow();
        if (user.isConfirmed()) {
            throw new ConflictInDatabaseException("Usuario ya confirmado");
        }
        user.setConfirmed(true);
        return userRepository.save(user).toDTO();
    }

    public UserDTO addAdmin(long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(Role.ADMINISTRATOR);
        return userRepository.save(user).toDTO();
    }

    public UserDTO removeAdmin(long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(Role.USER);
        return userRepository.save(user).toDTO();
    }

    private Set<UserDTO> toUsersDTO(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .map(User::toDTO)
                .collect(Collectors.toSet());
    }
}
