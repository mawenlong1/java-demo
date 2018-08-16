package com.mwl.lambda.list;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author mawenlong
 * @date 2018/08/16
 * describe:
 */
public class ComparatorDemo {
    final static List<Person> people = Arrays.asList(
            new Person("John", 20),
            new Person("Sara", 21),
            new Person("Jane", 21),
            new Person("Greg", 35));

    public static void sorted() {
        List<Person> ascendingAge =
                people.stream()
                        .sorted((person1, person2) -> person1.ageDifference(person2))
                        .collect(toList());
        List<Person> ascendingAge1 =
                people.stream()
                        .sorted(Person::ageDifference)
                        .collect(toList());
        //printPeople("Sorted in ascending order by age: ", ascendingAge);


        ascendingAge1.forEach(person -> System.out.println(person));

    }

    public static void sortedByName() {
        List<Person> ascendingAge = people.stream()
                .sorted((person1, person2) -> person1.getName().compareTo(person2.getName()))
                .collect(toList());
        ascendingAge.forEach(person -> System.out.println(person));
    }

    public static void sortedByNameAndAge() {
        final Function<Person, Integer> byAge = person -> person.getAge();
        final Function<Person, String> byTheirName = person -> person.getName();
        people.stream()
                .sorted(comparing(byAge).thenComparing(byTheirName))
                .collect(toList())
        .forEach(System.out::println);


    }

    public static void main(String[] args) {
        sortedByNameAndAge();
    }

}
