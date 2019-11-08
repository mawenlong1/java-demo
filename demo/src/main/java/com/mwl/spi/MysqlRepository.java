package com.mwl.spi;

/**
 * @author mawenlong
 * @date 2019/03/26
 */
public class MysqlRepository implements IRepository {
    @Override
    public void save(String data) {
        System.out.println("Save " + data + " to Mysql");
    }
}
