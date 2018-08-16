package com.mwl.lambda.list;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author mawenlong
 * @date 2018/08/16
 * describe:集合中查找元素
 */
public class Demo02 {
    final List<String> friends =
            Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    final List<String> editors =
            Arrays.asList("Brian", "Jackie", "John", "Mike");
    final List<String> comrades =
            Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");

    public void findItem() {
        final List<String> startsWithN =
                friends.stream()
                        .filter(name -> name.startsWith("N"))
                        .collect(Collectors.toList());
        System.out.println(String.format("Found %d names", startsWithN.size()));

    }

    public void findFilter() {
        //final Predicate<String> startsWithN = name -> name.startsWith("N");
        //final Function<String, Predicate<String>> startsWithLetter = (String letter) -> {
        //    Predicate<String> checkStarts = (String name) -> name.startsWith(letter);
        final Function<String, Predicate<String>> startsWithLetter =
                letter -> name -> name.startsWith(letter);

    final long countFriendsStartN =
            friends.stream()
                    .filter(startsWithLetter.apply("N"))
                    .count();
    final long countEditorsStartN =
            editors.stream()
                    .filter(startsWithLetter.apply("N"))
                    .count();
    final long countComradesStartN =
            comrades.stream()
                    .filter(startsWithLetter.apply("B"))
                    .count();

}

    public static void main(String[] args) {
        new Demo02().findItem();
    }

}
