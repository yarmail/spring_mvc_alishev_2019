package lesson21.controllers;

import lesson21.dao.PersonDAO;
import lesson21.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Урок 43
 * делаем выпадающий список
 * 1 метод показывает страницу с выпадающим списком
 * 2 метод принимает данные с выпадающего списка
 * и отображает данные в консоле о выбранном человеке
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());
        return "adminPage";
    }

    /**
     * "person" мы получаем с формы adminPage.html
     * В данной редакции мы просто выносим его id в консоль
     */
    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute ("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/people";
    }
}