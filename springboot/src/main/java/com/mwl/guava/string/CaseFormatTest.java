package com.mwl.guava.string;

import com.google.common.base.CaseFormat;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class CaseFormatTest {
  public static void main(String[] args) {
    String data = "test_data";
    System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, data));
    System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, data));
    System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, data));
  }
}
