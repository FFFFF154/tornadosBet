package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.box.tornadosbet.entity.mysql.Role;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.entity.postgresql.Division;
import ru.box.tornadosbet.exceptions.BoxerException;
import ru.box.tornadosbet.exceptions.UserNotFoundException;
import ru.box.tornadosbet.service.BoxerService;
import ru.box.tornadosbet.service.UserService;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    BoxerService boxerService;

    private String authenticationName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("authenticatedUser",
                userService.loadUserByUsername(authenticationName()));
        return "security/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws UserNotFoundException {
        if (userService.deleteUserById(id)){
            return "redirect:/admin";
        } else {
            throw new UserNotFoundException("This user does not exist");
        }

    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("updUser", userService.findById(id));
        model.addAttribute("checkRole", new Role()); // заменил UserRole на Role
        //model.addAttribute("checkAdmin", checkAdmin);
        return "security/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updUser") User updUser,
                             @ModelAttribute("checkRole") Role checkRole) throws UserNotFoundException{
        if (userService.updateUser(updUser, checkRole)) {
            return "redirect:/admin";
        } else {
            throw new UserNotFoundException("User not found");
        }
        //return "redirect:/update/" + updUser.getId();

    }

    @GetMapping("/admin/add-boxer")
    public String addBoxerForm(Model model){
        model.addAttribute("newBoxer", new Boxer());
        model.addAttribute("newCountry", new Country());
        model.addAttribute("newDivision", new Division());
        return "add-boxer";
    }

    @PostMapping("/admin/add-boxer") // TODO Добавить больше боксеров (ныне выступающих)
    public String addBoxer(@ModelAttribute("newBoxer") Boxer boxer,
                           @ModelAttribute("newCountry") Country country,
                           @ModelAttribute("newDivision") Division division)throws BoxerException{
        if(boxerService.addBox(boxer, country, division)){
            return "redirect:/top-boxers";
        } else {
            throw new BoxerException("Boxer hasn't been added");
        }


    }
}
