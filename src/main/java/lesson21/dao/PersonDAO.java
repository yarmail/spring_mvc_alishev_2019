package lesson21.dao;

import lesson21.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Урок 26
 * Оставляем метод index() на Statement
 * Остальные методы переписываем на PreparedStatement
 * пока без авто генерации id
 *
 * ---
 *
 * Урок 25.
 * В этом уроке  переходим с хранения
 * private List<Person> people;
 * на хранение в БД
 * Производим подключение к базе данных
 * в статических переменных
 * (помним, что по хорошему они должны храниться
 * в отдельном файле пропертиес)
 *
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
        Person person = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from person where id=?"
            );
            ps.setInt(1, id);
            ResultSet rs =  ps.executeQuery();
            rs.next();
            person = new Person();
            person.setId(rs.getInt("id"));
            person.setName(rs.getString("name"));
            person.setEmail(rs.getString("email"));
            person.setAge(rs.getInt("age"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     * Урок 26
     * Нужно переписать этот метод более безопасным
     * способом, через PrepareStatement
     * ---
     *
     * Урок 25
     * Сохранить человека
     * Проверочный запрос:
     * Запрос: localhost:8080/people/new
     *
     */
    public void save(Person person) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "insert  into person values (1, ?, ?, ?)"
            );
            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getEmail());
            ps.executeUpdate();
        } catch (SQLException e4) {
            e4.printStackTrace();
        }
    }

    /**
     * Урок 26
     * Обновляем все с использованием PreparedStatement
     */
    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "update person set name=?, age=?, email=? where id=?"
            );
            ps.setString(1, updatedPerson.getName());
            ps.setInt(2, updatedPerson.getAge());
            ps.setString(3, updatedPerson.getEmail());
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Урок 26
     * Обновляем все с использованием PreparedStatement
     */
    public void delete(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "delete from person where id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
Урок 26
Старый небезопасный вариант метода show()
с использованием Statement.
Практически везде теперь используем
PreparedStatement
.
    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e4) {
            e4.printStackTrace();
        }
    }
.

---

 index() - вернуть весь список
 show(int id) - вернуть одного, если есть

 Помечаем класс как @Component, стобы иметь возможность
 воспользоваться Spring и внедрить его где нам надо,
 в данном случае подключить к контроллеру

 (проверить, что папка сканируется можно в конфиге 16 урока)

 */