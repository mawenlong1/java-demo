package com.mwl.guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author mawenlong
 * @date 2018/11/26
 *
 *       双向关联 Bimap数据的强制唯一性
 *       对于反转后的map的所有操作都会影响原先的map对象
 */
public class Bimap {
  public static void main(String[] args) {
    BimapTest();
  }

  public static void BimapTest() {
    BiMap<Integer,String> logfileMap = HashBiMap.create();
    logfileMap.put(1,"a.log");
    logfileMap.put(2,"b.log");
    logfileMap.put(3,"c.log");
    System.out.println("原始map：");
    System.out.println("logfileMap:"+logfileMap);
    BiMap<String,Integer> filelogMap = logfileMap.inverse();
    System.out.println("filelogMap:"+filelogMap);

    logfileMap.put(4,"d.log");
    System.out.println("修改map后：");
    System.out.println("logfileMap:"+logfileMap);
    System.out.println("filelogMap:"+filelogMap);
  }
}
