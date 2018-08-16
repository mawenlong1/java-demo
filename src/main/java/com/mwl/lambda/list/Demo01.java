package com.mwl.lambda.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/15
 * describe:列表转化
 */
public class Demo01 {
    final List<String> friends =
            Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    public void printALl(){
        //friends.forEach((final String name)->System.out.println(name));
        //friends.forEach((name) -> System.out.println(name));
        friends.forEach(System.out::println);
    }
    public void uppercaseNames(){
        final List<String> uppercaseNames = new ArrayList<String>();
        //1.
        System.out.println("1.方式：");
        friends.forEach(name -> uppercaseNames.add(name.toUpperCase()));
        uppercaseNames.forEach(System.out::println);
        //2.
        System.out.println("2.方式：");
        friends.stream()
                .map(name->name.toUpperCase())
                .forEach(name -> System.out.print(name + " "));
        System.out.println("\n3.计算每个名字的字母个数：");
        friends.stream()
                .map(name -> name.length())
                .forEach(count -> System.out.print(count + " "));

        System.out.println("\n4.使用方法引用：");
        friends.stream()
                .map(String::toUpperCase)
                .forEach(name -> System.out.println(name));
    }

    public static void main(String[] args) {
        new Demo01().uppercaseNames();
    }



}
