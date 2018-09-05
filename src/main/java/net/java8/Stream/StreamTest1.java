package net.java8.Stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author eltons,  Date on 2018-09-05.
 */
public class StreamTest1 {
    @Test
    public void test1() {
        List<String> list = Arrays.asList("AA", "BB", "CC", "DD");

        //串行流
        Stream<String> stream = list.stream();
        //并行流，可以使用多个线程处理
        Stream<String> parallelStream = list.parallelStream();

        System.out.println(stream + "" + parallelStream);
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("AA", "BB", "CC", "DD");
        String[] str = {"aa", "bb"};


        //得到流的几种方式
        Stream<String> stream = list.stream();

        Stream<String> stream1 = Arrays.stream(str);

        Stream<String> stream3 = Stream.of("aa", "bb");

        //创建一个无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.forEach(System.out::println);

        //生成
        Stream<Double> stream5 = Stream.generate(() -> Math.random() * 10);
        stream5.forEach(System.out::println);
    }

    @Test
    public void test3() {
        Person p1 = new Person("张三", 12, "boy");
        Person p2 = new Person("张四", 22, "boy");
        Person p3 = new Person("张五", 23, "girl");
        Person p4 = new Person("张六", 24, "girl");


        List<Person> people1 = Arrays.asList(p1, p2);
        List<Person> people2 = Arrays.asList(p3, p4);

        List<List<Person>> people = Arrays.asList(people1, people2);

        people.stream().flatMap(List::stream)
                .forEach((x) -> System.out.println(x.getName()));


        Optional<Person> optional = people1.stream()
                .filter(x -> x.getName().equals("张四"))
                .findFirst();
        System.out.println(optional.get());
    }

    @Test
    public void parallelStream() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        //利用reduce规约来求和
        Optional<Integer> reduce = integers.stream().reduce((x, y) -> x + y);
        System.out.println(reduce.get());//45
    }

    @Test
    public void parallelStream1() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9);
        Stream<Integer> integerStream = integers.parallelStream();

        //reduce-sum模式
        Optional<Integer> reduce = integers.parallelStream().reduce(Integer::sum);
        System.out.println(reduce.get());//45

        HashSet<Integer> collect1 = integers.stream()
                .map(x -> x + 1)
                .collect(Collectors.toCollection(HashSet::new));
        Optional<Integer> collect = integers.stream()
                .map(x -> x + 1)
                .collect(Collectors.minBy(Integer::compare));
//        System.out.println(collect);

        Map<Integer, List<Integer>> collect2 = integerStream.collect(Collectors.groupingBy(Integer::new));

    }

    @Test
    public void groupTest() {
        Person p1 = new Person("张三", 12, "boy");
        Person p2 = new Person("张四", 22, "boy");
        Person p3 = new Person("张五", 13, "girl");
        Person p4 = new Person("张六", 24, "girl");
        List<Person> people1 = Arrays.asList(p1, p2, p3, p4);
        Stream<Person> stream = people1.parallelStream();

        //分组
//        Map<String, List<Person>> peopleMap = stream.collect(Collectors.groupingBy(Person::getStatus));
//        System.out.println(peopleMap);

        //多级分组
        Map<String, Map<String, List<Person>>> stringMapMap = stream.collect(Collectors.groupingBy(Person::getStatus, Collectors.groupingBy(
                p -> {
                    if (((Person) p).getAge() > 20) {
                        return "小孩子";
                    } else {
                        return "大孩子";
                    }
                }
        )));
        System.out.println(stringMapMap);

        //连接流中的数据字符串
        String nameStr = people1.stream().map(Person::getName)
                .collect(Collectors.joining("，", "head", "tail"));
        System.out.println(nameStr);
    }
}
