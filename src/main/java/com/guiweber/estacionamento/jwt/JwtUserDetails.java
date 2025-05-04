package com.guiweber.estacionamento.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Classe que representa os detalhes do usuário para autenticação via JWT.
 * Extende a classe User do Spring Security.
 */

public class JwtUserDetails extends User  {

    private com.guiweber.estacionamento.entities.User user;

    public JwtUserDetails(com.guiweber.estacionamento.entities.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().toString();
    }
}
