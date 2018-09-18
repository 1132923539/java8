package net.java8.thread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author eltons,  Date on 2018-09-18.
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        Book book = new Book();
        Runnable setBook = () -> {
            for (int i = 0; i < 100; i++) {
                book.set(i);
            }
        };
        Runnable read = () -> {
            for (int i = 0; i < 1000; i++) {
                book.get();
            }
        };

        new Thread(read, "读线程").start();
        new Thread(setBook, "写线程").start();
    }
}

class Book {
    private int number = 0;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读出了数据:" + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    //写
    public void set(int number) {
        lock.writeLock().lock();
        try {
            this.number = number;
            System.out.println(Thread.currentThread().getName() + "设置了书本数量为：" + number);
        } finally {
            lock.writeLock().unlock();
        }
    }

}
