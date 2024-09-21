package ru.box.tornadosbet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController { //TODO дополнить боксёрами
    //TODO Далее создать теоретический поединок

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }


}
