package com.mwl.guava.string;

import com.google.common.base.Splitter;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class SplitterTest {
    public static void main(String[] args) {
        System.out.println(Splitter.on(',').trimResults().omitEmptyStrings().split(
                "the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog."));
    }
}
