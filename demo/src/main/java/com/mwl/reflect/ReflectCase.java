package com.mwl.reflect;

import java.lang.reflect.Method;

/**
 * @author mawenlong
 * @date 2019-11-15 20:31
 * <p>
 * 通过Java的反射机制，可以在运行期间调用对象的任何方法
 */
public class ReflectCase {
    public static void main(String[] args) throws Exception {
        Proxy target = new Proxy();
        Method method = Proxy.class.getDeclaredMethod("run");
        method.invoke(target);

        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        String str5 = new String("ab");
        System.out.println(str5.equals(str3));
        System.out.println(str5 == str3);
        System.out.println(str5.intern() == str3);
        System.out.println(str5.intern() == str4);
    }

    static class Proxy {
        public void run() {
            System.out.println("run");
        }
    }
}
