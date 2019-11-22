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
        fun();
    }

    // 筛选法求质数
    private static void fun() {
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

        // 同时会生成堆中的对象以及常量池中hello的对象，但是此时s1是指向堆中的对象的
        String s1 = new String("hello");
        // 常量池中的已经存在
        String intern1 = s1.intern();
        String s2 = "hello";
        //false
        System.out.println(s1 == s2);
        //true 优先常量池
        System.out.println(intern1 == s2);
        // 此时生成了四个对象 常量池中的"hello1" + 2个堆中的"hello1" + s3指向的堆中的对象。（注：此时常量池不会生成"hello1hello1"）
        String s3 = new String("hello1") + new String("hello1");
        //常量池中没有“hello1hello1",常量池不仅仅可以存储对象，还可以存储对象的引用，会直接将s3的地址存储在常量池
        String intern3 = s3.intern();
        //常量池中的地址其实就是s3的地址
        String s4 = "hello1hello1";
        //intern 方法：当执行s3.intern()时,字符串常量池中存储一份s3的引用，这个引用指向堆中的字面量。
        //当运行到String s4 = "hello1hello1"时，发现字符串常量池已经存在一个指向堆中该字面量的引用，则返回这个引用，而这个引用就是s3。所以s3==s4输出true。
        //true
        System.out.println(s3 == s4);
        //true
        System.out.println(intern3 == s4);


    }

}
