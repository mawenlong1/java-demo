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
    }

    static class Proxy {
        public void run() {
            System.out.println("run");
        }
    }
}
