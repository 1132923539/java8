package net.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public class TimeTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //使用系统提供的格式化方式
//        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        //解析字符串为时间格式
        Callable c = () -> LocalDate.parse("2018-02-22", dtf);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        ArrayList<Future> futureTasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future submit = threadPool.submit(c);
            futureTasks.add(submit);
        }
        for (Future f : futureTasks) {
            Object o = f.get();
            System.out.println(o);
        }
    }

    @Test
    public void test2() {
        LocalDateTime time1 = LocalDateTime.now();
        System.out.println(time1);
        System.out.println(time1.toEpochSecond(ZoneOffset.ofHours(8)));

        //创建时间
        LocalDateTime time2 = LocalDateTime.of(2018, 12, 2, 19, 22, 45, 23);
        System.out.println(time2); //2018-12-02T19:22:45.000000023

        System.out.println(time2.plusMonths(2));//2019-02-02T19:22:45.000000023

        System.out.println(time1.getYear());
        System.out.println(time1.getMonthValue());
        System.out.println(time1.getDayOfMonth());
        System.out.println(time1.getHour());
        System.out.println(time1.getMinute());
        System.out.println(time1.getSecond());
    }

    //Instant 的使用
    @Test
    public void test3() {
        Instant now = Instant.now();//获得UTC时区的时间
        System.out.println(now.toEpochMilli());//1536228319118 toEpochMilli 用来转化时间为毫秒值

        OffsetDateTime localNow = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(localNow);

        Instant instant = Instant.ofEpochSecond(100);
        System.out.println(instant);
    }


    //Duration 计算两个时间之间的间隔
    //Period 计算两个日期之间的间隔
    @Test
    public void test4() throws InterruptedException {
        //计算时间间隔
        Instant ins1 = Instant.now();
        Thread.sleep(1000);
        Instant ins2 = Instant.now();

        long timeEpochMilli = Duration.between(ins1, ins2).toMillis();
        System.out.println(timeEpochMilli);

        //计算日期间隔
        LocalDate now = LocalDate.now();
        LocalDate last = LocalDate.of(2017, 4, 23);

        Period period = Period.between(last, now);
        System.out.println(period.getYears() + "年" + period.getMonths() + "月" + period.getDays() + "天");
    }

    @Test
    public void test5() {
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println(ldt1);

        LocalDateTime ldt2 = ldt1.withDayOfMonth(1);
        System.out.println(ldt2);

        //将时间设置为下周日
        LocalDateTime ldt3 = ldt1.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println(ldt3);

        //自定义调整规则
        LocalDateTime newDateTime = ldt1.with((temporal) -> {
            LocalDateTime dateTime = (LocalDateTime) temporal;
            LocalDateTime dateTime1 = dateTime.plusDays(1);
            return dateTime1;
        });
        System.out.println(newDateTime);
    }

    //对时区的操作
    @Test
    public void test6() {
        //查出所有时区
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(now);
    }
}
