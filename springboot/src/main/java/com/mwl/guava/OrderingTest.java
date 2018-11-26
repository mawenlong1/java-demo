package com.mwl.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class OrderingTest {
  public static void main(String[] args) {
    testStaticOrdering();
  }

  public static void testStaticOrdering() {
    ImmutableList<String> list = ImmutableList.of("peida", "jerry", "harry", "harry", "jhon",
        "neron");
    System.out.println(list);

    Ordering<String> naturalOrdering = Ordering.natural();
    Ordering<Object> usingToStringOrdering = Ordering.usingToString();
    Ordering<Object> arbitraryOrdering = Ordering.arbitrary();

    System.out.println("naturalOrdering:" + naturalOrdering.sortedCopy(list));
    System.out.println("usingToStringOrdering:" + usingToStringOrdering.sortedCopy(list));
    System.out.println("arbitraryOrdering:" + arbitraryOrdering.sortedCopy(list));
  }
}
