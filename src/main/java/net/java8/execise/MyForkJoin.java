package net.java8.execise;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public class MyForkJoin {

    @Test
    public void forkJoinTest() {
        Instant start = Instant.now();

        long reduce = LongStream.range(0, 10000000000L)
                //转化为并行流
                .parallel()
//                .reduce(0, Long::sum);
//                .count();
                .sum();
        System.out.println(reduce);

        Instant end = Instant.now();

        //Duration是统计间隔的工具类
        System.out.println("花费的时间为：" + Duration.between(start, end).toMillis());
    }
}
