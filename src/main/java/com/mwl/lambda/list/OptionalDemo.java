package com.mwl.lambda.list;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author mawenlong
 * @date 2018/08/16
 * describe:
 */
public class OptionalDemo {
    final static List<String> friends =
            Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

    public static void pickName(
            final List<String> names, final String startingLetter) {
        final Optional<String> foundName =
                names.stream()
                        .filter(name -> name.startsWith(startingLetter))
                        .findFirst();
        System.out.println(String.format("A name starting with %s: %s",
                startingLetter, foundName.orElse("No name found")));
        foundName.ifPresent(name -> System.out.println("Hello " + name));

    }

    public static void main(String[] args) {
        pickName(friends,"N");
    }
}
