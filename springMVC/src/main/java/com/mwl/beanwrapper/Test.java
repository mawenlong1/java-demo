package com.mwl.beanwrapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * BeanWrapper类的使用
 */
public class Test {
    public static void main(String[] args) {
        User user = new User();
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(user);
        beanWrapper.setPropertyValue("name", "张三");
        System.out.println("前:" + user);
        beanWrapper.setPropertyValue("name", "李四");
        System.out.println("后:" + user);
        // equals的默认实现是使用hashcode比较
        User user1 = new User();
        user1.setName("李四");
        System.out.println(user.equals(user1));
        System.out.println(user.hashCode() + "=====" + user1.hashCode());
    }
}
