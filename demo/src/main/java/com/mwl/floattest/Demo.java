package com.mwl.floattest;

/**
 * @author mawenlong
 * @date 2019/01/15
 */
public class Demo {
    private static final int NUM = Integer.MAX_VALUE;

    public static void main(String[] args) {
        float f = 1 << 24;
        System.out.println(f == f + 1);
        int sum = 0;
        Long start = System.nanoTime();
        for (int i = 0; i < NUM; i++) {
            int temp = i & 0x3f;
            sum += temp;
        }
        System.out.println("位运算时间：" + (System.nanoTime() - start));
        start = System.nanoTime();
        for (int i = 0; i < NUM; i++) {
            int temp = i % 64;
            sum += temp;
        }
        System.out.println(" 取余时间：" + (System.nanoTime() - start));
        System.out.println(sum);
    }
}

