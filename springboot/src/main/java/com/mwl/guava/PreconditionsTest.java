package com.mwl.guava;

import com.google.common.base.Preconditions;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class PreconditionsTest {
    public static void main(String[] args) {
        String name = "123";
        int age = -1;
        insert(name, age);
    }

    public static void insert(String name, int age) {
        Preconditions.checkNotNull(name, "%s不能为空", name);
        Preconditions.checkArgument(!name.isEmpty());
        Preconditions.checkArgument(age >= 0, "%s大于0", "age");
        System.out.println("name=" + name + "\nage=" + age);
    }
}
