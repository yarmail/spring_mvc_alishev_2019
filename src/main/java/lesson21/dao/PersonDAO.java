package lesson21.dao;

import lesson21.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * --
 * Урок 42 добавлено новое поле адрес.
 * Меняем save, update
 * далее меняем представление new
 *
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
     * Урок 41
     * Подключаем новый метод show для того, чтобы
     * проверить, есть ли в БД одинаковый email
     * Это нужно для продвинутой валидации
     * в классе PersonValidator
     * Старый вариант
     * .stream().findAny().orElse(null);
     * меняем на работу c Option
     */
    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person Where email=?",
                new Object[] {email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();

    }

    /**
     * Сохранить нового
     * Урок 31.
     * Переписываем этот метод с учетом того,
     * что автоинкременент мы передали БД
     *
     */
    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, age, email, address) values (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    /**
     * Обновить по id
     */
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, age=?, email=?, address=? where id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    ///////////////////////////////////
    //////// Урок 30
    //////// Тестируем производительность пакетной вставки
    //////////////////////////////////

    /**
     * Вставляет по одному
     * Замеряем время до и после вставки
     * Используем похожее на метод save
     */
    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for(Person person : people) {
            jdbcTemplate.update("insert into person values(?, ?, ?, ?)",
            person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    /**
     * Вставка 1000 записей одним пакетом
     * Для пакетной вставки есть отдельный метод batchUpdate
     */
    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into person values(?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, people.get(i).getId());
                        preparedStatement.setString(2, people.get(i).getName());
                        preparedStatement.setInt(3, people.get(i).getAge());
                        preparedStatement.setString(4,people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    /**
     * Урок 42
     * С добавлением нового поля адрес, обновляем метод
     */
    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name " + i, 30,
                    "test"+ i + "mail.ru", "some address"));
        }
        return people;
    }
}

/*
Урок 31
Переписываем метод save() с учетом того, что автоинкеменент
мы передали БД

---

Урок 30
Реализуем методы пакетной обработки запросов к БД
testMultipleUpdate() - вставляет по одному
testBatchUpdate() - отправляет 1 запрос с 1000 запросов в БД

---

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