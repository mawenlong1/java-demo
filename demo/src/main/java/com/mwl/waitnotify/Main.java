package com.mwl.waitnotify;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2019-09-15 14:01
 */
public class Main {

    public static void main(String[] args) {
        Vector sharedQueue = new Vector();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue), "Consumer");
        prodThread.start();
        consThread.start();
    }
}
