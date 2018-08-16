package com.mwl.lambda.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

/**
 * @author mawenlong
 * @date 2018/08/16
 * describe:
 */
public class MapReduceDemo {
    final static List<String> friends =
            Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

    public static void sumWords() {
        System.out.println("Total number of characters in all names: " + friends.stream()
                .mapToInt(name -> name.length())
                .sum());
    }
    /**
     * 求长度最长的name
     *      
     * @param 
     * @return void
     */
    public static void maxLongName() {
        final Optional<String> aLongName = friends.stream()
                .reduce((name1, name2) ->
                        name1.length() >= name2.length() ? name1 : name2);
        aLongName.ifPresent(name ->System.out.println(String.format("A longest name: %s", name)));
        final String steveOrLonger = friends.stream()
                .reduce("Sve", (name1, name2) ->
                        name1.length() >= name2.length() ? name1 : name2);

        System.out.println(steveOrLonger);

    }
    public static void change(){
        final String str = "w00t";
        str.chars()
                .mapToObj(ch -> Character.valueOf((char)ch))
                .forEach(System.out::println);
        System.out.println("=========================");
        str.chars()
                .filter(ch -> Character.isDigit(ch))
                .forEach(ch -> printChar(ch));
        System.out.println("=========================");
        str.chars()
                .filter(Character::isDigit)
                .forEach(MapReduceDemo::printChar);

    }
    private static void printChar(int aChar) {

        System.out.println((char)(aChar));

    }

    public static void main(String[] args) {
        //System.out.println(String.join(", ", friends));
        //System.out.println(
        //        friends.stream()
        //                .map(String::toUpperCase)
        //                .collect(joining(", ")));
        //maxLongName();
        change();

    }

}
