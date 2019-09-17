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
            System.out.println("生产:" + i);
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
                System.out.println("队列已满。" + Thread.currentThread().getName()
                                   + " 正在等待 , 队列大小: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            sharedQueue.notifyAll();
        }
    }

}
