package com.mwl.plugin;

import com.mwl.plugin.extension.ProcessExtension;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mawenlong
 * @date 2019/02/12
 */
public class ExtensionBuilder {
    private static final ExtensionBuilder INSTANCE = new ExtensionBuilder();

    private ExtensionBuilder() {
    }

    public static ExtensionBuilder getInstance() {
        return INSTANCE;
    }

    private Map<String, ProcessExtension> extMap = new ConcurrentHashMap<>();

    public void build() {
        try {
            //  找出所有注解了BizCode的类
            Reflections extReflections = new Reflections("com.mwl.plugin.extension");
            Set<Class<?>> extClasses = extReflections.getTypesAnnotatedWith(BizCode.class);
            for (Class<?> extClass : extClasses) {
                BizCode[] annotationType = extClass.getAnnotationsByType(BizCode.class);
                if (annotationType != null && annotationType.length > 0) {
                    BizCode bizCode = annotationType[0];
                    extMap.put(bizCode.value(), (ProcessExtension) extClass.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProcessExtension getExt(String bizCode) {
        return extMap.get(bizCode);
    }
}
