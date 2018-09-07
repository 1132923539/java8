package net.java8.interfaces;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public interface Haha {
    String name = "";

    void say();

    default String haha() {
        return "哈哈哈";
    }

    static void show() {
        System.out.println("这是接口中的静态方法");
    }
}
