package com.mwl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author mawenlong
 * @date 2019-03-19 21:08
 * <p>
 * https://mp.weixin.qq.com/s?__biz=MzI0NzEyODIyOA==&mid=2247484345&idx=1&sn=9ba0fb062ff6ca427710885d8a26bf0e&chksm=e9b58a72dec203649e2f5e7a97a4ddfcb46b96645a218c5c1ffd56ca832a184bdef0a6ecc923&scene=0&xtrack=1&key=b29fbb831ffce04cc3368387fa60d95841a32aa14af5bd8d439268ef1d714f18f66ecbfc6de4b98be4e9e38cdebabdd4eed48e3a9087f98a80ee3bc49f4bbce62ac10f20fbd22d3703f71546ce60a50b&ascene=1&uin=MTE5NzU2OTQxNw%3D%3D&devicetype=Windows+7&version=62060728&lang=zh_CN&pass_ticket=YuHG29BtivLe880Zvo4g2ZC%2Byh4az4A84MULpKmBm%2BmLlxvrMNXbKH%2Bz7NIpUpIX
 */
public class ReadByHeapByteBufferTest {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File data = new File("./data.txt");
        final FileChannel
                fileChannel = new RandomAccessFile(data, "rw").getChannel();
        final ByteBuffer buffer = ByteBuffer
                .allocate(4 * 1024 * 1024);
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            new Thread(new Runnable() {

                public void run() {
                    try {
                        fileChannel.read(buffer);
                        buffer.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
