package com.mwl.pattern.decorator;

/**
 * @author mawenlong
 * @date 2020-11-06 9:50 下午
 */
public abstract class AbstactDecoratorBattercake extends ABattercake {
    private ABattercake aBattercake;

    public AbstactDecoratorBattercake(ABattercake aBattercake) {
        this.aBattercake = aBattercake;
    }

    @Override
    protected String getDesc() {
        return aBattercake.getDesc();
    }

    protected abstract void doSomething();

    @Override
    protected int getPrice() {
        return aBattercake.getPrice();
    }
}
