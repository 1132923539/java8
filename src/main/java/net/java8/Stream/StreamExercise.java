package net.java8.Stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public class StreamExercise {
    Person p1 = new Person("张三", 12, "boy");
    Person p2 = new Person("张四", 22, "boy");
    Person p3 = new Person("张五", 13, "girl");
    Person p4 = new Person("张六", 24, "girl");
    List<Person> people1 = Arrays.asList(p1, p2, p3, p4);

    Transaction t1 = new Transaction("aa", 2014, 2000L);
    Transaction t2 = new Transaction("bb", 2013, 1000L);
    Transaction t3 = new Transaction("cc", 2016, 1150L);
    Transaction t4 = new Transaction("dd", 2015, 2300L);
    Transaction t5 = new Transaction("ee", 2014, 2200L);
    List<Transaction> transaction = Arrays.asList(t1, t2, t3, t4, t5);

    @Test
    //统计集合元素数目
    public void streamE() {
        Optional<Integer> reduce = people1.stream()
                .map((p) -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());
    }

    @Test
    //找出特定年份的记录，并且降序排列
    public void streamT() {

        List<Transaction> collect = transaction.stream()
                .filter(t -> t.getYear() == 2014)
                //添加负号表示降序排列
                .sorted((x, y) -> -Long.compare(x.getTrading(), y.getTrading()))
//                .forEach(System.out::println);
                .collect(Collectors.toList());
        System.out.println(collect);
    }


}
