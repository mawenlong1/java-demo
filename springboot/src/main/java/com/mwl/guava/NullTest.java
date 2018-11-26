package com.mwl.guava;

import com.google.common.base.Optional;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class NullTest {
  public static void main(String[] args) {
    NullTest guavaTester = new NullTest();
    Integer invalid = null;
    Optional<Integer> a = Optional.fromNullable(invalid);
    Optional<Integer> b = Optional.of(new Integer(10));
    System.out.println("====="+guavaTester.sum(a, b));
  }

  public Integer sum(Optional<Integer> a, Optional<Integer> b) {
    if( a.isPresent()==false){
      a= a.of(new Integer(10));
    }
    System.out.println("First parameter is present: " + a.isPresent());
    System.out.println("Second parameter is present: " + b.isPresent());
    return a.get() + b.get();
  }
}
