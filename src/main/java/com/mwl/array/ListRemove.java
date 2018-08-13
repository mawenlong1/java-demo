package com.mwl.array;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRemove {

    /**
     * list循环删除元素
     */
    public void testRemove() throws Exception{

//        forRemove();
//        forEachRemove();
//        iteratorRemove();
        mulThreadRemove();
        Thread.sleep(3000);
    }
    static ArrayList<String> arrayList = new ArrayList<String>();
    public void mulThreadRemove(){
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("2");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                Iterator<String> iterator = arrayList.iterator();
                while(iterator.hasNext()){
                    String s = iterator.next();
                    System.out.println(s);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            public void run() {
                Iterator<String> iterator = arrayList.iterator();
                while(iterator.hasNext()){
                    String s = iterator.next();
                    if(s.equals("2"))
                        iterator.remove();
                }
            };
        };
        thread1.start();
        thread2.start();
    }

    /**
     * for循环遍历删除
     */
    private void forRemove(){
        List<String> list = init();
        System.out.println("for循环遍历删除:");
        print(list);
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals("2")){
                list.remove(i);
            }
        }
        print(list);
    }
    /**
     * 增强for循环,会报这个ConcurrentModificationException异常
     */
    private void forEachRemove(){
        List<String> list = init();
        System.out.println("增强for循环:");
        print(list);
        for(String x:list){
            if(x.equals("2")){
                list.remove(x);
            }
        }
        print(list);
    }

    /**
     * iterator遍历删除
     */
    private void iteratorRemove(){
        List<String> list = init();
        System.out.println("iterator遍历删除:");
        Iterator<String> it = list.iterator();
        print(list);
        while(it.hasNext()){
            String x = it.next();
            if(x.equals("2")){
                it.remove();
            }
        }
        print(list);
    }



    private  List<String> init(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        return list;
    }
    private void print( List<String> list){
        for(String s:list){
            System.out.print(s+"\t");
        }
        System.out.println();
    }
}
