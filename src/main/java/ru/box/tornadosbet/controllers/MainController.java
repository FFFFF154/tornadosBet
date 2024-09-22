package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.box.tornadosbet.service.BoxerService;

@Controller
@Slf4j
public class MainController { //TODO дополнить боксёрами
    //TODO Далее создать теоретический поединок

    @Autowired
    private BoxerService boxerService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/top-boxers")
    public String boxers(Model model){
        model.addAttribute("boxers", boxerService.allBoxers());
        return "boxers";
    }

}
