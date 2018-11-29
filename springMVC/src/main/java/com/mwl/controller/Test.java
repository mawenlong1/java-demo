package com.mwl.controller;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
public abstract class Test {
    public abstract void test1();

    public void test2() {
        test1();
    }
}
