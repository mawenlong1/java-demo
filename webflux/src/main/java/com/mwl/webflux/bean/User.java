package com.mwl.webflux.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author mawenlong
 * @date 2018/12/19
 * @AllArgsConstructor会导致@Data不生成无参构造方法， 需要手动添加@NoArgsConstructor，
 * 如果没有无参构造方法, 可能会导致比如com.fasterxml.jackson在序列化处理时报错
 */
@Data
@AllArgsConstructor // 生成所有参数构造方法
@NoArgsConstructor
@Document
public class User {
    @Id
    private String id;
    @Indexed(unique = true) // 注解属性username为索引，并且不能重复
    private String username;
    private String phone;
    private String email;
    private String name;
    private Date birthday;
}
