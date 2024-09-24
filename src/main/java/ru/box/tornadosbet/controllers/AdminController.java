package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.box.tornadosbet.dto.UserRole;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.service.BoxerService;
import ru.box.tornadosbet.service.UserService;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    UserRole userRole;

    @Autowired
    BoxerService boxerService;

    private String authenticationName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "security/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("updUser", userService.findById(id));
        model.addAttribute("checkRole", userRole);
        //model.addAttribute("checkAdmin", checkAdmin);
        return "security/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updUser") User updUser,
                             @ModelAttribute("checkRole") UserRole checkRole) {
        if (userService.updateUser(updUser, checkRole)) {
            return "redirect:/admin";
        }
        return "redirect:/update/" + updUser.getId();

    }

    @GetMapping("/admin/add-boxer") //TODO Создать форму добавления боксеров
    public String addBoxerForm(Model model){
        model.addAttribute("newBoxer", new Boxer());
        model.addAttribute("newCountry", new Country());
        return "add-boxer";
    }

    @PostMapping("/admin/add-boxer") //TODO Добавление боксера в бд через сервис
    public String addBoxer(@ModelAttribute("newBoxer") Boxer boxer,
                           @ModelAttribute("newCountry") Country country){
        boxerService.addBox(boxer, country);
        return "redirect:/top-boxers";
    }
}
