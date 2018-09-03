package net.java8.lambda;

import org.junit.Test;

import java.util.*;
import java.util.function.*;

/**
 * @author eltons,  Date on 2018-08-31.
 */
public class Lambda1 {

    private List<String> aa = Arrays.asList(
            "asa", "bba", "cac", "dad"
    );

    @Test
    public void test1() {
        Collections.sort(aa, Comparator.comparingInt(String::length));
    }

    @Test
    public void test2() {
        int co = co(Arrays.asList(2, 3), (x, y) -> {
            x = x * x;
            return x * y;
        });
        System.out.println(co);
    }

    private int co(List<Integer> a, LambdaInterface li) {
        return li.operation(a.get(1), a.get(0));
    }

    @Test
    public void consumerTest0() {
        consumerTest(10, m -> System.out.println(m++ * 10));
    }

    private void consumerTest(Integer m, Consumer<Integer> consumer) {
        //这里调用accept 方法就是在调用传进来的lambda 表达式
        consumer.accept(m);
    }

    @Test
    public void supplierT() {
        List<Integer> integers = supplierTest(10, () -> (int) Math.random() * 10);
        for (Integer i : integers) {
            System.out.println(i);
        }

    }

    private List<Integer> supplierTest(int m, Supplier<Integer> supplier) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            integers.add(supplier.get());
        }

        return integers;
    }


    @Test
    public void funtionT() {
        Integer len = functionTest("哈啊哈", (str) -> str.length());
        System.out.println(len);
    }

    private Integer functionTest(String str, Function<String, Integer> function) {
        Integer newStr = function.apply(str);
        return newStr;
    }

    @Test
    public void predicateTest() {
        List<String> strings = Arrays.asList("aasds", "bbfds", "cc");
        List<String> strings1 = strFilter(strings, (str) -> str.length() > 2);
        System.out.println(strings1);
    }

    private List<String> strFilter(List<String> strs, Predicate<String> predicate) {
        ArrayList<String> strings = new ArrayList<>();
        for (String str : strs) {
            if (predicate.test(str)) {
                strings.add(str);
            }
        }
        return strings;
    }

    @Test
    public void tt() {
        Consumer<String> consumer = System.out::println;
        consumer.accept("test");
    }

    @Test
    public void instanceMTest() {
        BiConsumer<String, String> biConsumer = (x, y) -> x.equals(y);

        BiConsumer<String, String> bb = String::equals;
    }
}
