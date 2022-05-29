package lesson21.dao;

import lesson21.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream()
                .filter(person -> person.getId() == id)
                .findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
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