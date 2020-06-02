package hw3MapSet;

import java.util.*;

public class Main3 {

    public static void main(String[] args) {
        //task1
        System.out.println("task1:");
        String[] arr = {"water", "fire", "wind", "ear", "ear", "ear", "ear", "wind", "wind", "wind", "wind", "fire", "fire", "fire", "fire"};
        List<String> listWords = new ArrayList<>();
        listWords.addAll(Arrays.asList(arr));

        Map<String, Integer> mapWords = new HashMap();
        createMapCountersWords(listWords, mapWords);
        printUniqueWordsFromMap(mapWords);

        //task2
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("ivanov", 9999);
        phoneBook.add("ivanov", 9990);
        phoneBook.add("petrov", "8888");
        System.out.println("task2:");
        phoneBook.get("ivanov");
        phoneBook.get("petrov");
        phoneBook.get("sidorov");
    }





    public static void createMapCountersWords(List<String> listWords, Map<String, Integer> mapWords) {
        for (String word : listWords
        ) {
            mapWords.put(word, (mapWords.getOrDefault(word, 0)) + 1);
        }
    }

    public static void printUniqueWordsFromMap(Map<String, Integer> mapWords) {
        System.out.printf("Список уникальных слов:");
        mapWords.forEach((k, v) ->
                {
                    if (v == 1) {
                        System.out.printf(" %s", k);
                    }
                }
        );
        System.out.printf(".");
        System.out.println();
    }
}
