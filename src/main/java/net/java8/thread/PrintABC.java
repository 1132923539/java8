package net.java8.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author eltons,  Date on 2018-09-18.
 */
public class PrintABC {
    public static void main(String[] args) {
        Print1 print1 = new Print1();

        Runnable a = () -> {
            for (int i = 0; i < 20; i++) {
                print1.aPrint();
            }
        };
        Runnable b = () -> {
            for (int i = 0; i < 20; i++) {
                print1.bPrint();
            }
        };
        Runnable c = () -> {
            for (int i = 0; i < 20; i++) {
                print1.cPrint();
            }
        };

        new Thread(a, "A").start();
        new Thread(b, "B").start();
        new Thread(c, "C").start();
    }


}


class Print1 {

    private String flag = "A";
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void aPrint() {
        lock.lock();
        try {
            while (!"A".equals(flag)) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            flag = "B";
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    void bPrint() {
        lock.lock();
        try {
            while (!"B".equals(flag)) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            flag = "C";
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    void cPrint() {
        lock.lock();
        try {
            while (!"C".equals(flag)) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            flag = "A";
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}