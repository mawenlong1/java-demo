package com.mwl.guava.string;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/11/26
 */
public class JoinerTest {
    public static void main(String[] args) {
        ImmutableList<String> stringList = ImmutableList.of("peida", "jerry", "123", "harry", "harry",
                                                            "jhon", "neron");
        String res = Joiner.on("|").skipNulls().join(stringList); // 默认使用“|”作为分隔符
        System.out.println(res);
        res = Joiner.on("|").useForNull("no value").join("foo", "bar", "eee");
        System.out.println(res);
        // 连接map
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C", "Redskins");
        testMap.put("New York City", "Giants");
        testMap.put("Philadelphia", "Eagles");
        testMap.put("Dallas", "Cowboys");
        String returnedString = Joiner.on("#").withKeyValueSeparator("=").join(testMap);

        System.out.println(returnedString);
    }
}
