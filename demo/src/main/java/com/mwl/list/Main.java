package com.mwl.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mawenlong
 * @date 2019-10-02 19:16
 */
public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>() {{
            add("Hollis");
            add("hollischuang");
            add("H");
        }};

        List subList = names.subList(0, 1);
        //[Hollis] subList的类型不能为ArrayList
        System.out.println(subList);

        List<String> sourceList = new ArrayList<String>() {{
            add("H");
            add("O");
            add("L");
            add("L");
            add("I");
            add("S");
        }};
        List subList1 = sourceList.subList(2, 5);
        System.out.println("sourceList ： " + sourceList);
        System.out.println("sourceList.subList(2, 5) 得到List ：");
        System.out.println("subList ： " + subList1);
        subList1.set(1, "666");
        System.out.println("subList.set(3,666) 得到List ：");
        System.out.println("subList ： " + subList1);
        System.out.println("sourceList ： " + sourceList);
    }
}
