package com.mwl.thread;

/**
 * @author mawenlong
 * @date 2019-03-12 23:29
 * <p>
 * 奇线程打印奇数，偶线程打印偶数
 */
public class Main {
    private static int num = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (num <= 100) {
                if ((num & 1) == 0) {
                    synchronized (lock) {
                        System.out.println("偶数线程：" + num);
                        num++;
                    }

                }
            }
        });
        thread.setName("偶数线程");

        Thread thread1 = new Thread(() -> {
            while (num < 100) {
                if ((num & 1) == 1) {
                    synchronized (lock) {
                        System.out.println("奇数线程：" + num);
                        num++;
                    }
                }
            }
        });
        thread1.setName("奇数线程");
        thread.start();
        thread1.start();

    }
}
