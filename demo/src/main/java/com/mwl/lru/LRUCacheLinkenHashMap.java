package com.mwl.lru;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author mawenlong
 * @date 2019/04/26
 */
public class LRUCacheLinkenHashMap<K, V> {
    LinkedHashMap<K, V> cache;
    private int cacheSize;

    public LRUCacheLinkenHashMap(final int cacheSize) {
        // ceil浮点数向上取整数
        this.cacheSize = (int) Math.ceil(cacheSize / 0.75f) + 1;
        //boolean accessOrder用来控制访问顺序的，默认设置为false，在访问之后，不会将当前访问的元素插入到链表尾部
        cache = new LinkedHashMap<K, V>(this.cacheSize, 0.75f, true) {
            //内部类来重写removeEldestEntry()方法
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                System.out.println("size=" + size());
                //当前size()大于了cacheSize便删掉头部的元素
                return size() > cacheSize;
            }
        };
    }

    public V get(K key) {   //如果使用继承的话就用getE而不是get，防止覆盖了父类的该方法
        return (V) cache.get(key);
    }

    public V set(K key, V value) {
        return cache.put(key, value);
    }

    public void printCache() {
        for (Iterator it = cache.entrySet().iterator(); it.hasNext(); ) {
            Entry<K, V> entry = (Entry<K, V>) it.next();
            if (!"".equals(entry.getValue())) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
        System.out.println("------");
    }


    public static void main(String[] args) {
        LRUCacheLinkenHashMap<String, Integer> lrucache = new LRUCacheLinkenHashMap<String, Integer>(3);
        lrucache.set("aaa", 1);
        lrucache.printCache();
        lrucache.set("bbb", 2);
        lrucache.printCache();
        lrucache.set("ccc", 3);
        lrucache.printCache();
        lrucache.set("ddd", 4);
        lrucache.printCache();
        lrucache.set("eee", 5);
        lrucache.printCache();
        System.out.println("这是访问了ddd后的结果");
        lrucache.get("ddd");
        lrucache.printCache();
        lrucache.set("fff", 6);
        lrucache.printCache();
        lrucache.set("aaa", 7);
        lrucache.printCache();
    }

}
