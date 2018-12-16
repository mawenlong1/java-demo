package com.mwl.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/12/16
 */
public class LinkedMap {
    public static void main(String[] args) {
        final Map<String, String> test = new LinkedHashMap<String, String>(20, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                if (size() > 10) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        Map<String, String> test1 = new LinkedHashMap<>();
        for (int i = 0; i < 1000000; i++) {
            test.put("key=" + i, "value=" + i);
            test1.put("key=" + i, "value=" + i);
        }
        /**
         * 当LinkedHashMap设置accessOrder为false时按照输入顺序排序；accessOrder为true时按照读取顺序排序。
         * 设置为true的时候，不能使用这种遍历方法
         * 因为每次test1.get(key)的时候会将key对应的entry的位置提前，因此遍历的时候会报错。
         * 迭代器模式中遍历输出时是不允许修改集合结构的
         */
        // for (String key : test1.keySet()) {
        //     System.out.println(key + "-->" + test1.get(key));
        //     System.out.println(key + "-->" );
        // }

        //第一种：通过entrySet().iterator()遍历HashMap的key和映射的value,效率高
        Long begin = System.currentTimeMillis();
        Iterator iter = test1.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            // System.out.println(key + "-->" + val);
        }
        Long time1 = System.currentTimeMillis() - begin;
        //第二种：通过遍历keySet()遍历HashMap的value，效率低
        begin = System.currentTimeMillis();
        iter = test1.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = test1.get(key);
            // System.out.println(key + "-->" + val);
        }
        Long time2 = System.currentTimeMillis() - begin;
        System.out.println("第一种遍历需要的时间：" + time1);
        System.out.println("第二种遍历需要的时间：" + time2);
    }
}
