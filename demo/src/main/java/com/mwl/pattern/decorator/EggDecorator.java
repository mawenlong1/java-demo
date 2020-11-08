package com.mwl.pattern.decorator;

/**
 * @author mawenlong
 * @date 2020-11-06 9:52 下午
 */
public class EggDecorator extends AbstactDecoratorBattercake {
    public EggDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected void doSomething() {
    }

    public String test() {
        return "1111";
    }

    @Override
    protected String getDesc() {
        return super.getDesc() + " 加一个鸡蛋";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 2;
    }
}
