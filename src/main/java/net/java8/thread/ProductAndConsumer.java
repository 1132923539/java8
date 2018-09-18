package net.java8.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author eltons,  Date on 2018-09-18.
 */
public class ProductAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Runnable product = () -> {
            for (int i = 0; i < 100; i++) {
                clerk.get();
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 100; i++) {
                clerk.sale();
            }
        };

        new Thread(product, "生产者A").start();
        new Thread(consumer, "消费者A").start();
        new Thread(product, "生产者B").start();
        new Thread(consumer, "消费者B").start();
    }
}

class Clerk {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    //用来代替之前的wait等操作
    private Condition condition = lock.newCondition();

    //生产商品
    public void get() {
        lock.lock();
        try {
            while (product >= 10) {//自旋锁
                System.out.println(Thread.currentThread().getName() + ":仓库已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":生产商品:" + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //消费商品
    public void sale() {
        lock.lock();

        try {
            while (product <= 0) {
                System.out.println(Thread.currentThread().getName() + ":仓库中没有商品");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":卖出商品:" + product);
            product--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}


