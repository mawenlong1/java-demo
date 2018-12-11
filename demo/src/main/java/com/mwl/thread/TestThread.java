package com.mwl.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mawenlong
 * @date 2018/12/11
 */
public class TestThread {
  public static void main(String[] args) {
    ThreadCount tc = null;
    ExecutorService es = Executors.newCachedThreadPool();
    CompletionService<Integer> cs = new ExecutorCompletionService<>(es);
    for (int i = 0; i < 4; i++) {
      tc = new ThreadCount(i + 1);
      cs.submit(tc);
    }
    // 添加结束，及时shutdown，不然主线程不会结束
    es.shutdown();
    int total = 0;
    for (int i = 0; i < 4; i++) {
      try {
        total += cs.take().get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.out.println(total);
  }
}

class ThreadCount implements Callable<Integer> {
  private int type;

  ThreadCount(int type) {
    this.type = type;
  }

  @Override
  public Integer call() throws Exception {
    switch (type) {
    case 1:
      System.out.println("C盘统计大小");
      return 1;
    case 2:
      Thread.sleep(20000);
      System.out.println("D盘统计大小");
      return 2;
    case 3:
      System.out.println("E盘统计大小");
      return 3;
    case 4:
      System.out.println("F盘统计大小");
      return 4;
    default:
    }
    return null;
  }
}