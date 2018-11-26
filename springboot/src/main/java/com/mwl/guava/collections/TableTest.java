package com.mwl.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author mawenlong
 * @date 2018/11/26
 *
 *       两个键可以在组合的方式被指定为单个值。它类似于创建映射的映射。
 */
public class TableTest {
  public static void main(String[] args) {
    tableTest();
  }

  public static void tableTest() {
    Table<String, Integer, String> aTable = HashBasedTable.create();

    for (char a = 'A'; a <= 'C'; ++a) {
      for (Integer b = 1; b <= 3; ++b) {
        aTable.put(Character.toString(a), b, String.format("%c%d", a, b));
      }
    }

    System.out.println(aTable.column(2));
    System.out.println(aTable.row("B"));
    System.out.println(aTable.get("B", 2));

    System.out.println(aTable.contains("D", 1));
    System.out.println(aTable.containsColumn(3));
    System.out.println(aTable.containsRow("C"));
    System.out.println(aTable.columnMap());
    // rowMap()返回一个Map<R, Map<C, V>>的视图。rowKeySet()类似地返回一个Set<R>。
    System.out.println(aTable.rowMap());

    System.out.println(aTable.remove("B", 3));
  }
}
