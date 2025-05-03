package com.guiweber.estacionamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc //habilita o Spring MVC
@EnableWebSecurity //habilita a segurança do Spring
@Configuration //declara que esta classe é uma configuração do Spring
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf(csrf -> csrf.disable()) //desabilita o CSRF
               .formLogin(form -> form.disable()) //desabilita o login padrão do Spring Security
               .httpBasic(httpBasic -> httpBasic.disable()) //desabilita o login básico do Spring Security
               .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "api/v1/users").permitAll()
                       .anyRequest().authenticated()//permite o acesso a todos os endpoints
               ).sessionManagement(
                          session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //desabilita o gerenciamento de sessão
               ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //retorna o encoder de senha
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

