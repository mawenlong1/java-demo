package com.mwl.pattern;

/**
 * @author mawenlong
 * @date 2020-11-09 10:53 上午
 */
public class Main {


    public static void main(String[] args) {
        Thread t1 = new Thread(new PrintNum());
        Thread t2 = new Thread(new PrintNum());
        t1.start();
        t2.start();

    }
}

class PrintNum implements Runnable {
    private static int num = 1;

    @Override
    public void run() {
        synchronized (PrintNum.class) {
            for (int i = 0; i < 10; i++) {
                PrintNum.class.notifyAll();
                System.out.println(Thread.currentThread().getName() + "    " + num);
                num++;
                if (num > 10) {
                    return;
                }
                try {
                    PrintNum.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
