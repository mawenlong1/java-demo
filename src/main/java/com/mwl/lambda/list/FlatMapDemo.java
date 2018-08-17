package com.mwl.lambda.list;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author mawenlong
 * @date 2018/08/17
 * describe:
 */
public class FlatMapDemo {

    public static void betterWay() {
        List<File> files =
                Stream.of(new File(".").listFiles())
                        .flatMap(file -> file.listFiles() == null ?
                                Stream.of(file) : Stream.of(file.listFiles()))
                        .collect(toList());
        System.out.println("Count: " + files.size());

    }
    public static void watchFile() throws IOException {
        Path path = Paths.get(".");
        final WatchService watchService =
                path.getFileSystem()
                        .newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        System.out.println("Report any file changed within next 1 minute...");
    }

    public static void main(String[] args) throws IOException {
        watchFile();
    }
}
