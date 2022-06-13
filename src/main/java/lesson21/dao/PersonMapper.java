package lesson21.dao;

import lesson21.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Урок 27
 * Это замена работы с ResultSet в JDBC,
 * когда мы должны были бы обрабатывать ResultSet
 */
public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int i) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));
        person.setEmail(rs.getString("email"));
        person.setAge(rs.getInt("age"));
        return person;
    }
}