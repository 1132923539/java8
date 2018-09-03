package net.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author eltons,  Date on 2018-08-29.
 */
public class Lambda {
    @Test
    public void lambdaTest() {

        Runnable r1 = () -> System.out.println("格式一");

        Consumer<String> consumer = (a) -> System.out.println(a);

        Consumer<String> consumer1 = b -> System.out.println(b);


        Comparator<Integer> comparable = (a, b) -> {
            System.out.println(a + b);
            System.out.println(a - b);
            return a * b;
        };

        Comparator<Integer> comparator1 = (a, b) -> a * b;

        Comparator<Integer> comparator2 = (Integer a, Integer b) -> a * b;
    }
}
