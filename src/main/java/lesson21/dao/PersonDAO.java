package lesson21.dao;

import lesson21.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Урок 25.
 * В этом уроке  перходим с хранения
 * private List<Person> people;
 * на хранение в БД
 * Производим подключение к базе данных
 * в статических переменных
 * (помним, что по хорошему они должны храниться
 * в отдельном файле пропертиес)
 * ---
 *
 * Урок 24.
 * В связи с изменением основной модели Person
 * (добавления в неё полей) приходится поменятть и здесь
 *
 */
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Урок 25
     * Показать всех людей
     * Запрос: localhost:8080/people
     * (сработало)
     */
    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "select * from person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        return null;
    }

    /**
     * Урок 25
     * Сохранить человека
     * Проверочный запрос:
     * Запрос: localhost:8080/people/new
     *
     */
    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e4) {
            e4.printStackTrace();
        }
    }

    /**
     * К уроку 23
     * добавили параметры в уроке 24
     */
    public void update(int id, Person updatedPerson) {
    }

    /**
     * К уроку 23
     */
    public void delete(int id) {

    }
}

/*
 index() - вернуть весь список
 show(int id) - вернуть одного, если есть

 Помечаем класс как @Component, стобы иметь возможность
 воспользоваться Spring и внедрить его где нам надо,
 в данном случае подключить к контроллеру

 (проверить, что папка сканируется можно в конфиге 16 урока)

 */