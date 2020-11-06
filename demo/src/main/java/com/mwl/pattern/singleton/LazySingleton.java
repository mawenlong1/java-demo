package com.mwl.pattern.singleton;

/**
 * @author mawenlong
 * @date 2020-11-06 11:03 上午
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
