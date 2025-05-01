package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.User;
import com.guiweber.estacionamento.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords not match");
        }
        User u = findById(id);
        if (!u.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Current password not match");
        }
        u.setPassword(newPassword);
        return u;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
