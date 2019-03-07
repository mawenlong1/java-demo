package com.mwl.proxy.cglib;

import com.mwl.proxy.UserManager;
import com.mwl.proxy.UserManagerImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author mawenlong
 * @date 2019/03/07
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
            throws Throwable {
        System.out.println("cglib动态代理，监听开始！" + method.getName());
        Object res = method.invoke(target, objects);
        System.out.println("cglib动态代理，监听结束！");
        return res;
    }

    public Object getCglibProxy(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        Object result = enhancer.create();
        return result;
    }

    public static void main(String[] args) {
        CglibProxy cglib = new CglibProxy();
        UserManager userManager = (UserManager) cglib.getCglibProxy(new UserManagerImpl());
        userManager.delUser("admin");
        userManager.addUser("123", "123");
        System.out.println(userManager.getClass());
    }
}
