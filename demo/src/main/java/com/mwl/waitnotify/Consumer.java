package com.mwl.waitnotify;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2019-09-15 14:00
 */
public class Consumer implements Runnable {


    private final Vector sharedQueue;

    public Consumer(Vector sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("消费: " + consume());
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(Consumer.class.getName());
            }
        }
    }

    private int consume() throws InterruptedException {

        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("队列以空。" + Thread.currentThread().getName()
                                   + "正在等待 , 大小: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }

        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            return (Integer) sharedQueue.remove(0);
        }
    }
}
