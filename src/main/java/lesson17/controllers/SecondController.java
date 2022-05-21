package lesson17.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {
    @GetMapping("/exit")
    public String exit() {
        return "second/exit";
    }
}

/*
Урок 17 подсоединяем контролллеры
 */
