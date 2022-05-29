package lesson21.models;

public class Person {
    private int id;
    private String name;

    /**
     * Урок 22.
     * Добавили новый пустой конструктор
     * для того чтобы отправить пустой объект
     * для создания Person (заполнения формы)
     * на странице people/new
     */
    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
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
}
