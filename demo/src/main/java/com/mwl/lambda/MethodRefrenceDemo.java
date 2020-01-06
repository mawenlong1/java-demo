package com.mwl.lambda;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

/**
 * @author mawenlong
 * @date 2018/12/18
 */
public class MethodRefrenceDemo {
    public static void main(String[] args) {
        // 静态方法的方法引用。
        IntUnaryOperator methodRefrence1 = DemoClass::staticMethod;
        System.out.println(methodRefrence1.applyAsInt(111));

        DemoClass demo = new DemoClass();

        // 实例方法normalMethod的方法引用。
        IntUnaryOperator methodRefrence2 = demo::normalMethod;
        System.out.println(methodRefrence2.applyAsInt(111));

        // 对同一个实例方法normalMethod也可以使用静态引用。
        // 代码上normalMethod虽然只有一个参数,但实际上有一个隐含的this函数
        // 所以使用的是2个参数bifunction函数接口
        BiFunction<DemoClass, Integer, Integer> methodRefrence3 = DemoClass::normalMethod;
        System.out.println(methodRefrence3.apply(demo, 111));
    }
}
