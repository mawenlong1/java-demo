package com.mwl.plugin.extension;

import com.mwl.plugin.BizCode;

/**
 * @author mawenlong
 * @date 2019/02/12
 * <p>
 * 京东活动处理
 */
@BizCode("jd")
public class JdProcessExtension implements ProcessExtension {
    @Override
    public void beforeProcess(String params, StringBuilder processRecord) {
        System.out.println("京东活动前置处理流程");
        processRecord.append("京东活动前置处理流程 - ");
    }

    @Override
    public void afterProcess(String params, StringBuilder processRecord) {
        System.out.println("京东活动后置处理流程");
        processRecord.append("京东活动后置处理流程");
    }
}
