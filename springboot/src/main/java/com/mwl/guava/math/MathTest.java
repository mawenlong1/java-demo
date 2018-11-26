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
    IntMath.checkedAdd(1, 2);
    LongMath.checkedAdd(1, 2);
    BigIntegerMath.factorial(2);
  }
}
