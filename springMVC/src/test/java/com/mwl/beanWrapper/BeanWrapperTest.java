package com.mwl.beanWrapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
public class BeanWrapperTest {
    public static void main(String[] args) {
        User user = new User();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(user);
        bw.setPropertyValue("name", "张三");
        System.out.println(user.getName());
        bw.setPropertyValue("name", "李四");
        System.out.println(user.getName());

    }
}
