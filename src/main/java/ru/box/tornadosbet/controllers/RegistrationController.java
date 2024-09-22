package ru.box.tornadosbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registrationForm(Model model){
        model.addAttribute("regForm", new User());
        return "security/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("regForm") User user, Model model){
        if (userService.saveUser(user)){
            model.addAttribute("checkReg", "User created successfully");
            return "redirect:/login";
        } else {
            model.addAttribute("checkReg", "User doesn't created");
            return "redirect:/security/registration";
        }


    }
}
