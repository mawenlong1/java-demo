package com.mwl.lambda.list;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author mawenlong
 * @date 2018/08/17
 * describe:
 */
public class CollectDemo {
    final static List<Person> people = Arrays.asList(
            new Person("John", 20),
            new Person("Sara", 21),
            new Person("Jane", 21),
            new Person("Greg", 35));

    public static void add() {
        List<Person> olderThan20 =
                people.stream()
                        .filter(person -> person.getAge() > 20)
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<Person> olderThan201 =
                people.stream()
                        .filter(person -> person.getAge() > 20)
                        //类似还有toSet(),toMap(),joining()拼接字符串,
                        .collect(toList());
        Map<Integer, List<Person>> peopleByAge =
                people.stream()
                        .collect(groupingBy(Person::getAge));
        Map<Integer, List<String>> nameOfPeopleByAge =
                people.stream()
                        .collect(groupingBy(Person::getAge, mapping(Person::getName, toList())));



        System.out.println("Grouped by age: " + peopleByAge);
        System.out.println("People older than 20: " + olderThan20);

        System.out.println("People older than 20: " + olderThan201);
    }
    //按名字的首字母进行分组，然后选出每个分组中年纪最大的那位。
    public static void  groupedBy(){
        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> oldestPersonOfEachLetter =
                people.stream()
                        .collect(groupingBy(person -> person.getName().charAt(0),
                                reducing(BinaryOperator.maxBy(byAge))));
        System.out.println("Oldest person of each letter:");
        System.out.println(oldestPersonOfEachLetter);
    }
    public static void main(String[] args) {
        groupedBy();
    }

}
