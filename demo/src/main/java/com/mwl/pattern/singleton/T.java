package com.mwl.pattern.singleton;

/**
 * @author mawenlong
 * @date 2020-11-06 11:01 上午
 */
public class T implements Runnable {
    @Override
    public void run() {
        LazySingleton lazySingleton = LazySingleton.getInstance();
        System.out.println(Thread.currentThread().getId() + "   " + lazySingleton);

    }
}
