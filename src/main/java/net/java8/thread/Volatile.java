package net.java8.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author eltons,  Date on 2018-09-15.
 */

public class Volatile {
    //    private Boolean flags = false;
    AtomicBoolean flags = new AtomicBoolean(false);

    @Test
    public void test1() {
        Runnable t1 = () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            flags.compareAndSet(false, true);
            System.out.println("flag已经改变为true");
        };
        new Thread(t1).start();

        while (true) {
            //若flag没有加volatile，循环会永远进行下去
            if (flags.get()) {
                System.out.println("--------------");
                break;
            }
        }
    }

    @Test
    public void test2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);

        Runnable t1 = () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            for (int i = 0; i < 10; i++) {

                System.out.println(i);
            }
            latch.countDown();
        };

        for (int i = 0; i < 10; i++) {
            new Thread(t1).start();
        }

        //这里会等待上面所有线程都运行完，
        latch.await();

        System.out.println("最终输出");
    }
}
