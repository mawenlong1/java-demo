package com.mwl.plugin.extension;

import com.mwl.plugin.BizCode;

/**
 * @author mawenlong
 * @date 2019/02/12
 * <p>
 * 淘宝活动处理
 */
@BizCode("taobao")
public class TaobaoProcessExtension implements ProcessExtension {

    @Override
    public void beforeProcess(String params, StringBuilder processRecord) {
        System.out.println("淘宝活动前置处理流程");
        processRecord.append("淘宝活动前置处理流程 - ");
    }

    @Override
    public void afterProcess(String params, StringBuilder processRecord) {
        System.out.println("淘宝活动后置处理流程");
        processRecord.append("淘宝活动后置处理流程");
    }
}
