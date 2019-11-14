package com.mwl.thread;

/**
 * @author mawenlong
 * @date 2019-03-12 23:53
 */
public class Main2 {

    private static final Object lock = new Object();
    private static int count = 0;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            while (count <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    count++;
                    lock.notifyAll();

                    try {
                        if (count <= 100) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.setName("偶数线程");

        Thread thread1 = new Thread(runnable);
        thread1.setName("奇数线程");


        thread.start();
        thread1.start();
    }


}
