package lesson21.dao;

import lesson21.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Урок 27
 * Очередной раз переписываем DAO на JdbcTemplate
 * После создания в SpringConfig двух бинов
 * dataSource() и jdbcTemplate()
 * все настройки уходят туда
 * ---
 * для работы с jdbcTemplate
 * в качестве 2 аргумента мы должны указать RowMapper
 * Это такой объект, который отображает строки из таблицы
 * каждую строку, полученую в результате этого запроса
 * из нашей таблицы Person он отобразит в объект класса
 * Person. Row mapper мы реализуем самостоятельно
 * в классе PersonMapper
 * Если названия колонок совпадают с названиями полей,
 * то вместо RowMapper мы можем использовать
 * встроенный маппер BeanPropertyRowMapper
 *
 * Проверочный запрос: localhost:8080/people
 */
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Показать всех
     */
    public List<Person> index() {
        return jdbcTemplate.query("select * from person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    /**
     * Показать одного, если есть
     */
    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    /**
     * Сохранить нового
     */
    public void save(Person person) {
        jdbcTemplate.update("insert into person values (1, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail());
    }

    /**
     * Обновить по id
     */
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, age=?, email=? where id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }
}

/*

Урок 27
Примеры с использованием RowMapper, который мы делаем сами
в классе PersonMapper.
.
    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }
.
    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }
.


*/