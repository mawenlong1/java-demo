package com.mwl.proxy.jdk;

import com.mwl.proxy.UserManager;
import com.mwl.proxy.UserManagerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author mawenlong
 * @date 2019/03/07
 */
public class JdkProxy implements InvocationHandler {
    Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK动态代理，监听开始！" + method.getName());
        Object res = method.invoke(target, args);
        System.out.println("JDK动态代理，监听结束！");
        return res;
    }

    private Object getJDKProxy(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                      target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        JdkProxy jdkProxy = new JdkProxy();
        UserManager userManager = (UserManager) jdkProxy.getJDKProxy(new UserManagerImpl());
        userManager.delUser("333");
        userManager.addUser("123", "123");
        System.out.println(userManager);
    }
}
