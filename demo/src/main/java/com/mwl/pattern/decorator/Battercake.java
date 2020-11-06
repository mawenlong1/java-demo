package com.mwl.pattern.decorator;

/**
 * @author mawenlong
 * @date 2020-11-06 9:49 下午
 */
public class Battercake extends ABattercake {
    @Override
    protected String getDesc() {
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
