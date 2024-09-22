package ru.box.tornadosbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.box.tornadosbet.dto.UserRole;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.service.UserService;

@Controller
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    UserRole userRole;

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
}
