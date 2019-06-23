package com.mwl.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mawenlong
 * @date 2018/11/30
 * 现在有一个非常庞大的数据，假设全是 int 类型。现在我给你一个数，你需要告诉我它是否存在其中(不需要特别的准确尽量高效)。
 */
public class BloomFilterTest {

    private final int NUM = 10000000;

    /**
     * 在1000w数据时内存溢出
     */
    @Test
    public void hashMapTest() {
        long star = System.currentTimeMillis();
        Set<Integer> hashset = new HashSet<>(NUM);
        for (int i = 0; i < NUM; i++) {
            hashset.add(i);
        }
        Assert.assertTrue(hashset.contains(1));
        Assert.assertTrue(hashset.contains(2));
        Assert.assertTrue(hashset.contains(3));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }

    /**
     * 在1000w数据时内存溢出
     */
    @Test
    public void bloomFilterTest() {
        long star = System.currentTimeMillis();
        BloomFilters bloomFilters = new BloomFilters(NUM);
        for (int i = 0; i < NUM; i++) {
            bloomFilters.add(i + "");
        }
        Assert.assertTrue(bloomFilters.check(1 + ""));
        Assert.assertTrue(bloomFilters.check(2 + ""));
        Assert.assertTrue(bloomFilters.check(3 + ""));
        Assert.assertTrue(bloomFilters.check(999999 + ""));
        Assert.assertFalse(bloomFilters.check(400230340 + ""));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }

    @Test
    public void guavaTest() {
        long star = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), NUM, 0.01);
        for (int i = 0; i < NUM; i++) {
            filter.put(i);
        }
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(NUM));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }

}
