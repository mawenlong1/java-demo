package com.mwl.engine;

import com.mwl.plugin.ExtensionBuilder;
import com.mwl.plugin.extension.ProcessExtension;

/**
 * @author mawenlong
 * @date 2019/02/12
 */
public class ExtensionEngine implements Engine {
    public ExtensionEngine() {
        ExtensionBuilder.getInstance().build();
    }

    @Override
    public String process(String bizCode, String params) {
        StringBuilder processRecord = new StringBuilder();

        ProcessExtension processExtension = ExtensionBuilder.getInstance().getExt(bizCode);
        // 1、前置处理
        processExtension.beforeProcess(params, processRecord);

        // 2、统一处理
        System.out.println("统一处理流程");
        processRecord.append("统一处理 - ");

        // 3、后置处理
        processExtension.afterProcess(params, processRecord);

        return processRecord.toString();
    }
}
