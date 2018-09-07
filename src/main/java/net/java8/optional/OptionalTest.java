package net.java8.optional;

import net.java8.Stream.Person;
import org.junit.Test;

import java.util.Optional;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public class OptionalTest {

    @Test
    public void optionalTest() {
        Optional<Person> person = Optional.of(new Person());
        System.out.println(person.get());

        //创建一个空的optional
        Optional<Person> person1 = Optional.empty();
//        person1.get();

        Optional<Person> person2 = Optional.ofNullable(null);
        person2.get();

    }
}
