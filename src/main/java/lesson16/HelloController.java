package lesson16;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello-world")
    public String sayHello() {
        return "hello_world";
    }
}
/*
Аннотация @GetMapping.
Когда запрос приходит на нужный адрес,
выполнить метод и вернуть представление (view)
(в нашем случае
src/main/webapp/WEB-INF/views/hello_world.html
 */
