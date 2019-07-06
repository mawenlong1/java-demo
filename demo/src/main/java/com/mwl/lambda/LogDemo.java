package com.mwl.lambda;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mawenlong
 * @date 2018/12/18
 * <p>
 * lambda的惰性求值
 */
public class LogDemo {
    private static final Logger logger = Logger
            .getLogger(LogDemo.class.getName());

    @Override
    public String toString() {
        System.out.println("这个方法执行了, 耗时1秒钟");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        return "LogDemo";
    }

    public void test() {
        // 打印日志前需要先判断日志级别
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("正常判断：打印一些日志:" + this);
        }
        // 如果不加判断直接打印, 会有额外多余的开销, 就算最终日志并没有打印
        //执行代码，发现虽然日志没有打印，但toString方法还是执行了，属于多余浪费的开销。
        logger.fine("不进行判断：打印一些日志:" + this);
        logger.fine(() -> "lambda的惰性求值：打印一些日志:" + this);

    }

    public static void main(String[] args) {
        LogDemo demo = new LogDemo();
        demo.test();
    }
}
