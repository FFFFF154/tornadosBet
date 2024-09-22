package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.service.UserService;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login-page")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new User());
        return "security/login";
    }
}
