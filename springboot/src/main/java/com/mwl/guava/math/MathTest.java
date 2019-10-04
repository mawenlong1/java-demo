package com.mwl.guava.math;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class MathTest {
    public static void main(String[] args) {
        System.out.println(IntMath.checkedAdd(1, 2));
        System.out.println(LongMath.checkedAdd(1, 2));
        System.out.println(BigIntegerMath.factorial(2));
    }
}
