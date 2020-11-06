package com.mwl.pattern.builder;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @author mawenlong
 * @date 2020-11-06 9:42 上午
 */
public class   Main {
    public static void main(String[] args) {
        Set<String> set = ImmutableSet.<String>builder().add("dd").add("232").build();
        System.out.println(set);

    }
}
