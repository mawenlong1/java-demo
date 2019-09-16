package com.mwl.waitnotify;

import jdk.nashorn.internal.runtime.logging.Logger;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2019-09-15 13:58
 */
@Logger
public class Producer implements Runnable {

    private final Vector sharedQueue;
    private final int SIZE;

    public Producer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Produced:" + i);
            try {
                produce(i);
            } catch (InterruptedException ex) {
                System.out.println(Producer.class.getName());
            }
        }
    }

    private void produce(int i) throws InterruptedException {
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                                   + " is waiting , size: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            sharedQueue.notifyAll();
        }
    }

}
