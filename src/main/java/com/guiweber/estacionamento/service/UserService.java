package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.User;
import com.guiweber.estacionamento.exception.EditPasswordException;
import com.guiweber.estacionamento.exception.UserNotFoundException;
import com.guiweber.estacionamento.exception.UsernameUniqueViolationException;
import com.guiweber.estacionamento.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format("Username %s already exists", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new EditPasswordException("Passwords not match");
        }
        User u = findById(id);
        if (!u.getPassword().equals(currentPassword)) {
            throw new EditPasswordException("Current password not match");
        }
        u.setPassword(newPassword);
        return u;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
