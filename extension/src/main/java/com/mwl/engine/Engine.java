package com.mwl.engine;

/**
 * @author mawenlong
 * @date 2019/02/12
 * <p>
 * 执行引擎
 */
public interface Engine {
    String process(String bizCode, String params);
}
