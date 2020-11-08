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
        EggDecorator eggDecorator = new EggDecorator(aBattercake);
        System.out.println(aBattercake.getDesc() + "------" + aBattercake.getPrice() + "---" + eggDecorator.test());

        Integer i = 500;
        Integer i1 = 500;
        System.out.println(i == i1);
        int a = 0;
        for (a = 0; a < 10; a++) {
            if (a == 3) {
                continue;
            }
            System.out.println(a);
        }
        int[] nums = { 3, 3, 2, 1, 2, 44, 22, 767, 1, 44, 22 };
        System.out.println("==============");
        System.out.println(fun(nums));
        int[] nums1 = { 3, 3, 2, 1, 2, 44, 22, 1, 44, 22, 777, 767 };
        System.out.println("==============");
        System.out.println(fun1(nums1)[0] + "     " + fun1(nums1)[1]);
    }

    //一个数组只有一个出现过一次，求这个数字
    public static int fun(int[] nums) {
        int tmp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            tmp = nums[i] ^ tmp;
        }
        return tmp;
    }

    //一个数组只有两个出现过一次，求这个数字
    public static int[] fun1(int[] nums) {
        int[] res = { 0, 0 };
        int tmp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            tmp = nums[i] ^ tmp;
        }
        tmp = lowBit(tmp);
        for (int i = 0; i < nums.length; i++) {
            if ((tmp & nums[i]) == 0) {
                res[0] ^= nums[i];
            } else {
                res[1] ^= nums[i];
            }
        }
        return res;
    }

    public static int lowBit(int x) {
        return x & -x;
    }
}
