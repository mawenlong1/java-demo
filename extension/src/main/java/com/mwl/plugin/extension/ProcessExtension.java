package com.mwl.plugin.extension;

/**
 * @author mawenlong
 * @date 2019/02/12
 * <p>
 * 扩展点
 */
public interface ProcessExtension {
    /**
     * 前置处理
     */
    void beforeProcess(String params, StringBuilder processRecord);

    /**
     * 后置处理
     */
    void afterProcess(String params, StringBuilder processRecord);
}
