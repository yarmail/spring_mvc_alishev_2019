package lesson21.models;


import javax.validation.constraints.*;

/**
 * Урок 24 добавляем полей и добавляем
 * аннотаций hibernate для валидации этих полей
 * Это удобно для форм
 * ---
 * Урок 42 Добавляем поле адрес с каскадным изменением всего и вся
 * Добавляем новое поле в базу данных
 * у адреса должна быть следующая структура:
 * Страна, Город, индекс (6 цифр), например
 * Russia, Moscow, 123456
 */
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,
            message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    //Russia, Moscow, 123456
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
        message = "Your address should be in this format: Country, City, Postal Code (6digits)")
    private String address;

    /**
     * Урок 22.
     * Добавили новый пустой конструктор
     * для того чтобы отправить пустой объект
     * для создания Person (заполнения формы)
     * на странице people/new
     */
    public Person() {
    }

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}