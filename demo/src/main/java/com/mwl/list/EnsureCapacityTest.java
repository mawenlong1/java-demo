package com.mwl.list;

import java.util.ArrayList;

/**
 * @author mawenlong
 * @date 2020-05-26 02:51
 */
public class EnsureCapacityTest {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        final int N = 10000000;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        System.out.println("未使用EnsureCapacity方法：" + (System.currentTimeMillis() - begin));

        ArrayList<Object> list1 = new ArrayList<>();
        begin = System.currentTimeMillis();
        list1.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list1.add(i);
        }
        System.out.println("使用EnsureCapacity方法：" + (System.currentTimeMillis() - begin));
    }
}
