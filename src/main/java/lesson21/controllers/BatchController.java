package lesson21.controllers;

import lesson21.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер, который обрабатывает запросы на
 * указанные адреса
 */
@Controller
@RequestMapping("/test-batch-update")
public class BatchController {
    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    /**
     * Этот метот обрабатывает запросы
     * по ссылке ....
     * 1000 запросов по одному
     *
     */
    @GetMapping("/without")
    public String withoutBath() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    /**
     * Этот метот обрабатывает запросы
     * по ссылке ....
     * 1000 запросов одним пакетом
     * (1 запрос к БД на 1000 операций)
     */
    @GetMapping("/with")
    public String with() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}