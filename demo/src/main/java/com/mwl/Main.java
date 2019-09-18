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

}
