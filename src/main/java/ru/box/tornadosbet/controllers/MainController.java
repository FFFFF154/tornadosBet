package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.box.tornadosbet.service.BoxerService;
import ru.box.tornadosbet.service.UserService;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private BoxerService boxerService;

    @Autowired
    private UserService userService;

    private String authenticationName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("authenticatedUser",
                userService.loadUserByUsername(authenticationName()));
        return "welcome";
    }

    @GetMapping("/top-boxers")
    public String boxers(Model model){
        model.addAttribute("authenticatedUser",
                userService.loadUserByUsername(authenticationName()));
        model.addAttribute("boxers", boxerService.allBoxers());
        return "boxers";
    }

    @GetMapping("/versus")
    public String versus(Model model){ // TODO Сделать выбор 2х боксеров
        model.addAttribute("boxers", boxerService.allBoxers());
        return "versus";
    }

}
