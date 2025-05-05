package com.guiweber.estacionamento.jwt;

import com.guiweber.estacionamento.entities.User;
import com.guiweber.estacionamento.entities.enums.Role;
import com.guiweber.estacionamento.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthentication(String username) {
        Role role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().toString().substring("ROLE_".length()));
    }
}
