package com.mwl.beanwrapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.jws.soap.SOAPBinding.Use;

public class Test {
    public static void main(String[] args) {
        User user = new User();
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(user);
        beanWrapper.setPropertyValue("name","张三");
        System.out.println(user);
        beanWrapper.setPropertyValue("name","李四");
        System.out.println(user);
    }
}
