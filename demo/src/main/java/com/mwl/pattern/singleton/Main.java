package com.mwl.pattern.singleton;

/**
 * @author mawenlong
 * @date 2020-11-06 11:11 上午
 */
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new T());
        Thread t2 = new Thread(new T());
        t1.start();
        t2.start();
        System.out.println("end"+LazySingleton.getInstance());
    }
}
