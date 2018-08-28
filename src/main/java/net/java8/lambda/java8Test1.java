package net.java8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author eltons,  Date on 2018-08-28.
 */
public class java8Test1 {


    @Test
    public void arraysTest() {
        List<String> strings = Arrays.asList("a", "b", "c");

        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);

        strings.forEach(System.out::print);

        strings.stream().filter(it -> it.length() > 2)
                .map(String::getBytes)
                .forEach(System.out::println);


    }
}
