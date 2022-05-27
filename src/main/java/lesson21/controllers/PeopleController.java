package lesson21.controllers;

import lesson21.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
}

/*
Создаем контроллер @Controller
Помечаем @RequestMapping
(все адреса будут начинаться с /people )

Первый метод index()
возвращает название представления,
в котором будут отображаться все люди
из DAO и передает этих людей на представление
@GetMapping() пустой т.к. запрос
общий - /people
В качестве параметра внедряем в него model
для передачи в нем людей

---

Второй метод show и его аннотация
@PathVariable позволяет получить
доступ к любому пользователю
с каким-то id
Мы получием любого человека с id из
DAO и передадим этого человека на отображение
в представление

---

Внедрение нужного DAO происходит
    @Autowired
    private PersonDAO personDAO;
Idea предупреждает, что лучше производить
внедрение через конструктор
(над ним также можно поставить @Autowired
по желанию)
Теперь мы можем использовать это поле
для наших нужд






 */
