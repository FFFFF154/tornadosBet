package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.box.tornadosbet.entity.User;
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

//    @PostMapping("/login-page")
//    public String login(@ModelAttribute("loginForm") User user, Model model){
//        //TODO аутентификая + авторизация
//        if (userService.authentication(user)){
//            log.info("Success authentication");
//            return "redirect:/welcome";
//        } else {
//            //model.addAttribute("errorLogin", "user doesn't authenticated");
//            return "redirect:/login-page";
//        }
//    }
}
