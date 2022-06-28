package lesson21.util;

import lesson21.dao.PersonDAO;
import lesson21.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Урок 41
 * Создаем расширенный валидатор из пакета springframework
 * для более сложных случаев чем валидация на морде,
 * - валидация сообщений базы данных (ошибка 500)
 *
 * Для каждой сущности обычно создается свой валидатор
 *
 * В supports подключаем нужный класс, который будем валидировать
 *
 * Для того, чтобы проверить дубликат email при регистрации,
 * мы должны проверить есть ли такой же email в базе данных,
 * для этого нам нужно в ДАО добавить метод - поиск по email
 * В этом классе подключаем DAO
 *
 * Данный созданный валидатор мы будем использовать
 * в контроллере
 *
 */
@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    /**
     * Первый аргумент - поле, с которым ошибка
     * Второй аргумент - код ошибки
     * Третий - сообщение об ошибке
     */
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
    }
}
