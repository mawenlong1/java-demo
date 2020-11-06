package com.mwl.pattern.decorator;

/**
 * @author mawenlong
 * @date 2020-11-06 9:54 下午
 */
public class Main {
    public static void main(String[] args) {
        ABattercake aBattercake;
        aBattercake = new Battercake();
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new SausageDecorator(aBattercake);
        System.out.println(aBattercake.getDesc() + "------" + aBattercake.getPrice());
    }
}
