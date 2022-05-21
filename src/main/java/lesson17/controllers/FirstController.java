package lesson17.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hello")
    public String helloPage() {
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}

/*
Урок 17 подсоединяем контролллеры

Считается хорошим тоном для контроллера
FirstController хранить страницы
представления в папке first

 */
