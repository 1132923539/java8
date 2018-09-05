package net.java8.Stream;

import java.util.Objects;

/**
 * @author eltons,  Date on 2018-09-05.
 */
public class Person {

    private String name;
    private int age;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(status, person.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, status);
    }

    public Person() {
    }

    public Person(String name, int age, String status) {
        this.name = name;
        this.age = age;
        this.status = status;
    }
}
