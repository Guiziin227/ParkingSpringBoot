package com.guiweber.estacionamento.config;

import com.guiweber.estacionamento.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Configuração de segurança do Spring
 * <p>
 * Esta classe configura a segurança do Spring, desabilitando o CSRF, o login padrão e o gerenciamento de sessão.
 * Também define as permissões de acesso para os endpoints da API e adiciona um filtro de autorização JWT.
 * <p>
 * @author Gui Weber
 * @version 1.0
 */
@EnableWebMvc //habilita o Spring MVC
@EnableMethodSecurity //habilita a segurança do Spring
@Configuration //declara que esta classe é uma configuração do Spring
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf(csrf -> csrf.disable()) //desabilita o CSRF
               .formLogin(form -> form.disable()) //desabilita o login padrão do Spring Security
               .httpBasic(httpBasic -> httpBasic.disable()) //desabilita o login básico do Spring Security
               .authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                        .anyRequest().authenticated()
        )//permite o acesso a todos os endpoints
               .sessionManagement(
                          session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //desabilita o gerenciamento de sessão
               ).addFilterBefore(
                          jwtAuthorizationFilter(), //adiciona o filtro de autorização JWT
                          UsernamePasswordAuthenticationFilter.class
               )
               .build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(); //retorna o filtro de autorização JWT
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

