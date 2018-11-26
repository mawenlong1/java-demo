package com.mwl.guava.collections;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/11/26
 *
 *       允许重复，但是不保证顺序。
 */
public class MultisetTest {
  static String strWorld = "wer|dffd|ddsa|dfd|dreg|de|dr|ce|ghrt|cf|gt|ser|tg|ghrt|cf|gt|"
      + "ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr|wer|dffd|ddsa|dfd|dreg|de|dr|"
      + "ce|ghrt|cf|gt|ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr";
  static String[] words = strWorld.split("\\|");

  public static void main(String[] args) {
    testWordCount();
    testMultsetWordCount();
  }

  public static void testWordCount() {

    Map<String, Integer> countMap = Maps.newHashMap();
    for (String word : words) {
      Integer count = countMap.get(word);
      if (count == null) {
        countMap.put(word, 1);
      } else {
        countMap.put(word, count + 1);
      }
    }
    System.out.println("countMap：");
    for (String key : countMap.keySet()) {
      System.out.println(key + " count：" + countMap.get(key));
    }
  }

  public static void testMultsetWordCount() {

    List<String> wordList = new ArrayList<String>();
    for (String word : words) {
      wordList.add(word);
    }
    Multiset<String> wordsMultiset = HashMultiset.create();
    wordsMultiset.addAll(wordList);

    for (String key : wordsMultiset.elementSet()) {
      System.out.println(key + " count：" + wordsMultiset.count(key));
    }
    System.out.println("wordList的大小："+wordList.size());
    System.out.println("wordsMultiset的大小："+wordsMultiset.size());
  }
}
