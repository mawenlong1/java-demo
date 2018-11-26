package com.mwl.guava;

import static com.google.common.collect.Maps.newHashMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class GoogleCollections {
  public static void main(String[] args) {
    Map<String, Map<Long, List<String>>> map = new HashMap<String, Map<Long, List<String>>>();
    Map<String, Map<Long, List<String>>> map1 = newHashMap();

    List<String> list = Lists.newArrayList();
    list.add("a");
    list.add("b");
    list.add("c");
    list.add("d");
    ImmutableList<String> of = ImmutableList.of("a", "b", "c", "d");
    ImmutableMap<String,String> map2 = ImmutableMap.of("key1", "value1", "key2", "value2");


  }
}
