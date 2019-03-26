package com.mwl.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author mawenlong
 * @date 2019/03/26
 */
public class Main {
  public static void main(String[] args) {
    //内部使用当前线程ContextClassLoader,取去加载类
    ServiceLoader serviceLoader = ServiceLoader.load(IRepository.class);
    Iterator it = serviceLoader.iterator();
    while (it != null && it.hasNext()) {
      IRepository demoService = (IRepository) it.next();
      System.out.println("class:" + demoService.getClass().getName());
      demoService.save("tom");
    }

  }
}
