package lesson20.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(
            @RequestParam (value = "name", required = false) String name,
            @RequestParam (value = "surname", required = false) String surname,
            Model model) {
        //System.out.println("Hello, " + name + " " + surname);
        model.addAttribute("message", "Hello, " + name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}

/*
В уроке 17 делали 1 способом
через объект HttpServletRequest request получали
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
все параметры.
Теперь, в 20 уроке, делаем другим способом -
через RequestParam

Что происходит:
Мы из get запроса пользователя, получаем значения ключей
name и surname и сохраняем их в String переменные
Далее ? мы внедряем объект model и создаем ключ - значение
Ключ "message"
Значение  "Hello" + name + " " + surname

Далее, в представлении мы будем вытягивать эти данные из model

 */
