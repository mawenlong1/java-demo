package com.mwl.lambda.list;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author mawenlong
 * @date 2018/08/17
 * describe:
 */
public class FileDemo {

    public static void listFiles() throws IOException {
        Files.list(Paths.get("."))
                //只显示当前目录
                .filter(Files::isDirectory)
                .forEach(System.out::println);
        //列出隐藏文件
        new File(".").listFiles(File::isHidden);
    }
    public static void listFiles1() throws IOException {
        Files.newDirectoryStream(
                Paths.get("E:\\scripts"), path -> path.toString().endsWith(".py"))
                .forEach(System.out::println);
    }
    public static void main(String[] args) throws IOException {
        listFiles1();
    }
}
