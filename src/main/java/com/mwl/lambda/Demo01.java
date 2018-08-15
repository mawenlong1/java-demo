package com.mwl.lambda;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/15
 * describe:
 */
public class Demo01 {
    final List<BigDecimal> prices = Arrays.asList(
            new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
            new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"),
            new BigDecimal("45"), new BigDecimal("12"));

    /**
     * 计算大于20需要打八折(普通循环)
     *
     * @param
     * @return void
     */
    public void totalOfDiscountedPrices(){
        BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;
        for(BigDecimal price : prices) {
            if(price.compareTo(BigDecimal.valueOf(20)) > 0){
                totalOfDiscountedPrices =
                        totalOfDiscountedPrices.add(price.multiply(BigDecimal.valueOf(0.9)));
            }
        }
        System.out.println("普通循环：Total of discounted prices: " + totalOfDiscountedPrices);
    }
    /**
     * 计算大于20需要打八折(lambda表达式)
     *
     * @param
     * @return void
     */
    public void totalOfDiscountedPrices1(){
        final BigDecimal totalOfDiscountedPrices =
                prices.stream()
                        //过滤出大于20的价格
                        .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                        //映射为打9折
                        .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                        //求和
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("lambda表达式：Total of discounted prices: " + totalOfDiscountedPrices);
    }

    public static void main(String[] args) {
        new Demo01().totalOfDiscountedPrices();
        new Demo01().totalOfDiscountedPrices1();
    }
}
