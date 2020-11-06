package com.mwl.pattern.decorator;

/**
 * @author mawenlong
 * @date 2020-11-06 9:53 下午
 */
public class SausageDecorator extends AbstactDecoratorBattercake {
    public SausageDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected void doSomething() {

    }

    @Override
    protected String getDesc() {
        return super.getDesc() + "加一个香肠";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 1;
    }
}
