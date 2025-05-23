package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Usuario;
import com.guiweber.estacionamento.entities.enums.Role;
import com.guiweber.estacionamento.exception.EditPasswordException;
import com.guiweber.estacionamento.exception.EntityNotFoundException;
import com.guiweber.estacionamento.exception.UsernameUniqueViolationException;
import com.guiweber.estacionamento.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario save(Usuario user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format("Username %s already exists", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public Usuario editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new EditPasswordException("Passwords not match");
        }
        Usuario u = findById(id);
        if (!passwordEncoder.matches(currentPassword, u.getPassword())) {
            throw new EditPasswordException("Current password not match");
        }
        u.setPassword(passwordEncoder.encode(newPassword));
        return u;
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public Role findRoleByUsername(String username) {
        return userRepository.findByRole(username);
    }
}
