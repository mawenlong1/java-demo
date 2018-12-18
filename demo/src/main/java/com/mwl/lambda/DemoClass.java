package com.mwl.lambda;

/**
 * @author mawenlong
 * @date 2018/12/18
 */
public class DemoClass {
    /**
     * 这里是一个实例方法
     */
    public int normalMethod(DemoClass this, int i) {
        System.out.println("实例方法可以访问this:" + this);
        return i * 2;
    }

    /**
     * 这里是一个静态方法
     */
    public static int staticMethod(int i) {
        return i * 2;
    }

    public static void main(String[] args) {
        DemoClass demo2 = new DemoClass();
        // 代码定义上有2个参数, 第一个参数为this
        // 但实际上调用的时候只需要一个参数
        demo2.normalMethod(1);


    }
}
