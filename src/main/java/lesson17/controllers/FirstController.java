package lesson17.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        System.out.println("Hello, " + name + " " + surname);


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

---

Перехватываем обращения, которые начинаются на
first
@RequestMapping("/first")
Например:
first/hello или first/goodbye

---

Урок 19

Внедряем объект request - все что связано с гет запросом
public String helloPage(HttpServletRequest request)
Представим, что URL пользователь будет передавать имя и фамилию
Выведем их на экран
Для проверки делаем запрос:
localhost:8080/first/hello?name=Tom&surname=Jones
Ввод в консоли
Hello, Tom Jones
Если мы НЕ передаем параметры этот способ пишет
Hello null, null

---

Такой же результат мы можем получить сделав 2 способом
public String helloPage(@RequestParam("name") String name,
                        @RequestParam("surname") String surname)
Если мы здесь НЕ передаем параметры
мы получаем ошибку 400 Bad request
Если мы пишем
@RequestParam("name", required = false) String name
то не будет ошибки и будет лежать null


 */
