package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

}
