package net.java8.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author eltons,  Date on 2018-09-18.
 */
public class CallableTest {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Callable<Integer> callable = () -> {
            int sum = 0;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sum = sum + i;
            }
            return sum;
        };

        //执行Callable接口的内容
        FutureTask<Integer> ft = new FutureTask<>(callable);
        new Thread(ft).start();
        System.out.println(ft.get());

        //ft.get()在前面，因此执行完Callable线程才会执行下面，类似闭锁效果
        System.out.println("---------------");
    }

    @Test
    public void test2() {
        final int[] ticket = {100};
        Lock lock = new ReentrantLock();

        Runnable runnable = () -> {
            while (true) {
                lock.lock();
                try {
                    if (ticket[0] > 0) {
                        System.out.println(Thread.currentThread().getName() + "卖出了第：" + ticket[0] + "张票");
                        ticket[0]--;
                    }
                } finally {
                    lock.unlock();
                }

            }

        };

        for (int i = 0; i < 3; i++) {
            System.out.println("--");
            new Thread(runnable, i + "号").start();
        }


    }
}
