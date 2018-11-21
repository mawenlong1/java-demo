package com.mwl.lamada;

@FunctionalInterface
public interface WorkerInterface {
    void doSomeWork();

    default WorkerInterface negate() {
        return () -> {
            doSomeWork();
            System.out.println("implment");
        };
    }

    default void testDefault() {
        System.out.println("testDefault");
    }
}
