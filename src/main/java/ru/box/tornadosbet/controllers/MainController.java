package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.box.tornadosbet.dto.Bid;
import ru.box.tornadosbet.dto.BoxerChoice;
import ru.box.tornadosbet.dto.WinningOdds;
import ru.box.tornadosbet.entity.Count;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.service.BoxerService;
import ru.box.tornadosbet.service.UserService;

import javax.swing.*;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private BoxerService boxerService;

    @Autowired
    private UserService userService;

    private String authenticationName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("authenticatedUser",
                userService.loadUserByUsername(authenticationName()));
        return "welcome";
    }

    @GetMapping("/top-boxers")
    public String boxers(Model model) {
        model.addAttribute("authenticatedUser",
                userService.loadUserByUsername(authenticationName()));
        model.addAttribute("boxers", boxerService.allBoxers());
        return "boxers";
    }

    @GetMapping("/versus") // Выбираем 2х боксеров
    public String versusForm(Model model) {
        model.addAttribute("firstBoxer", new Boxer());
        model.addAttribute("secondBoxer", new Boxer());
        model.addAttribute("boxers", boxerService.allBoxers());
        return "versus";
    }

    @PostMapping("/versus") //Получаем 2х боксеров (запоминаем)
    public String versus(@ModelAttribute("firstBoxer") Boxer firstBoxer,
                         @ModelAttribute("secondBoxer") Boxer secondBoxer,
                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("firstBoxer", firstBoxer);
        redirectAttributes.addAttribute("secondBoxer", secondBoxer);
        return "redirect:/versus-continue";
    }

    @GetMapping("/versus-continue") // Делаем ставку
    public String versusBetForm(@RequestParam(name = "firstBoxer") Boxer firstBoxer,
                                @RequestParam(name = "secondBoxer") Boxer secondBoxer,
                                Model model) {
        WinningOdds winningOdds = new WinningOdds(firstBoxer, secondBoxer);
        model.addAttribute("firstBoxer", firstBoxer);
        model.addAttribute("secondBoxer", secondBoxer);
        model.addAttribute("winningOdds", winningOdds);
        //log.warn(winningOdds.toString());
        model.addAttribute("bid", new Bid()); // Ставка
        model.addAttribute("choice", new BoxerChoice());
        return "versus-bet";
    }

    @PostMapping("/versus-continue") // Принимаем ставку, переход на страницу выигрыша/поражения
    public String versusBet(@ModelAttribute("bid") Bid bid,
                            @ModelAttribute("winningOdds") WinningOdds winningOdds,
                            @ModelAttribute("choice") BoxerChoice choice,
                            RedirectAttributes redirectAttributes) {
        if(userService.transactionToAdmin(
                (User) userService.loadUserByUsername(authenticationName()),
                bid.getBid())){
            redirectAttributes.addFlashAttribute("winningOdds", winningOdds);
            redirectAttributes.addFlashAttribute("choice", choice);
            redirectAttributes.addFlashAttribute("bid", bid);
            return "redirect:/result";
        }

        return "redirect:/versus";
    }

    @GetMapping("/result") // Страница результатов ставки
    public String result(@ModelAttribute("winningOdds") WinningOdds winningOdds,
                         @ModelAttribute("choice") BoxerChoice choice,
                         @ModelAttribute("bid") Bid bid,
                         Model model) {
        Boxer boxerWin = boxerService.winner(winningOdds);
        if (userService.checkWin(
                boxerWin,
                choice,
                (User) userService.loadUserByUsername(authenticationName()),
                boxerService.prize(boxerWin, winningOdds, bid))) {
            model.addAttribute("result", "You win");
        } else {
            model.addAttribute("result", "You lose");
        }
        return "result";
    }

    @GetMapping("/donate")
    public String donateForm(Model model){
        model.addAttribute("donateCount", new Count());
        return "donate";
    }

    @PostMapping("/donate")
    public String donate(@ModelAttribute("donateCount") Count count){
        userService.donateToUser((User) userService.loadUserByUsername(authenticationName()), count);
        return "redirect:/welcome";
    }
}
