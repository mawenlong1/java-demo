package com.mwl;

import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019-07-05 13:10
 * <p>
 * 筛法求素数
 */
public class Main {
    public static void main(String[] args) {
        testConstantPool();
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            int[] array = new int[n];
            // 初始化筛数组
            for (int i = 2; i < n; i++) {
                array[i] = i;
            }
            // 如果没被筛出，则记录，并将temp的倍数筛出
            for (int i = 2; i < n; i++) {
                if (array[i] != 0) {
                    int j, temp;
                    temp = array[i];
                    for (j = 2 * temp; j < n; j += temp) {
                        array[j] = 0;
                    }
                    System.out.print(array[i] + " ");
                }
            }
        }
    }

    private static void testConstantPool() {
        String a = "xx";
        String b = "xx";
        String c = "xxx";
        String d = new String("xx");
        //true  jvm现在常量池中创建了"xx"，a指向"xx", 创建b时，先会看常量池中是否有"xx",如果有 让b 指向"xx";如果没有 则新建一个。
        System.out.println(a == b);
        // false
        System.out.println(a == c);
        //==是比较的内存地址
        System.out.println(a == d);
    }

}
