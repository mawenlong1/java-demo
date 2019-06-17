package com.mwl.bridge;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        C c = new D();
        System.out.println(D.class.getMethods());
        try {
            c.id(new Object());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Method method : D.class.getMethods()) {
            System.out.println(method.getName());
        }
    }
}
