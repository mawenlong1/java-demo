package com.mwl.waitnotify;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2019-09-15 14:00
 */
public class Consumer implements Runnable {


    private final Vector sharedQueue;
    private final int SIZE;

    public Consumer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumer: " + consume());
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(Consumer.class.getName());
            }
        }
    }

    private int consume() throws InterruptedException {

        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                                   + " is waiting , size: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }

        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            return (Integer) sharedQueue.remove(0);
        }
    }
}
