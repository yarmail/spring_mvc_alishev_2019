package lesson21.controllers;

import lesson21.dao.PersonDAO;
import lesson21.models.Person;
import lesson21.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Урок 24 Переделываем отдельные методы под (собака) Valid
 * Получается что-то подобное
 * (собака)Valid Person person, BindingResult bindingResult
 * Урок 41 Внедряем расширенный валидатор для email
 *
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
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

    /**
     * Урок 22 Создание нового Person этап 1
     * По GET запросу мы отправляем пользователю
     * форму для создания нового Person
     * Если мы создаем нового Person мы должны
     * передать пустой объект объект для
     * заполнения формы Person
     * <p>
     * можно заменить model.addAttribute на конструкцию
     * из метода create и будет работать также
     */
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    /**
     * Урок 22 Создание нового Person этап 2
     * На вход мы никакого адреса не получаем,
     * PostMapping() пустой (на адрес /people ?), т.к.
     * запрос получаем через метод Post
     * Здесь мы должны получить данные из формы
     * и положить их в новый объект
     * и новый объект положить в БД
     * Используем 2 вариант ModelAttribute
     * который берет данные из формы и кладет
     * их в объект Person
     * После выполнения всех работ мы делаем редирект
     * на страницу /people - показать всех
     * ---
     * Урок 24 добавляем анотацию @Valid
     * после того, добавили валидацию в Person
     * Сразу после объекта, который должен быть валидирован
     * ставится объект  BindingResult bindingResult
     * в который помещаются ошибки, получается так:
     * (собака)Valid Person person, BindingResult bindingResult
     * Если в форме ошибки есть, возвращаем обратно форму создания
     * if (bindingResult.hasErrors()
     * return "people/new"
     * При возврате формы у нас уже будут ошибки,
     * которые мы сможем показать
     * с помощью Thymeleaf
     * ---
     * Урок 41 Внедряем расширенный валидатор для email
     *
     */
    @PostMapping()
    public String create(@ModelAttribute("person")
        @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    /**
     * Урок 23 Редактирование person этап 1
     * Мы получаем из запроса id.
     * По этому id мы вызываем из базы данные этого пользователя
     * и заполняем его данными модель person,
     * которую будем отправлять в форму edit.html
     * для того, чтобы там не была пустая форма
     * и с ними переходим на страницу edit
     * для заполнения
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    /**
     * Урок 23 Обновление данных о Person этап 2
     * Принимаем объект person из формы
     * уже с измененными данными
     * Мы находим по id в БД person
     * и меняем его
     * ---
     * Урок 24 добавляем валидацию
     * далее редактируем представления
     * ---
     * Урок 41 Внедряем расширенный валидатор для email
     * personValidator.validate(person, bindingResult);
     *
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("person")
        @Valid Person person, BindingResult bindingResult,
        @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    /**
     * Урок 23 Реализуем удаление человека
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}

    /**
     * Урок 22.
     * при аннотировании метода ведет себя
     * следующим образом:
     * работает как ключ - "headerMessage"
     * значение - "Welcome to our website"
     * (типа Model, только глобально)
     * для всего контроллера
     */
/*  Пока не очень хорошо работает

    @ModelAttribute ("headerMessage")
    public String populateHeaderMessage() {
        return "Welcome to our website";
    }
}*/

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