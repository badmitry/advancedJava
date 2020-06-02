package hw3MapSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhoneBook {
    private Map<String, List<Integer>> phoneBook = new HashMap<>();

    public void add(String surname, int phoneNumber) {
        if (phoneBook.get(surname) == null) {
            List<Integer> listNumbers = new ArrayList<>();
            listNumbers.add(phoneNumber);
            phoneBook.put(surname, listNumbers);
        } else {
            phoneBook.get(surname).add(phoneNumber);
        }
    }

    public void add(String surname, String phoneNumber) {
        if (phoneBook.get(surname) == null) {
            List<Integer> listNumbers = new ArrayList<>();
            listNumbers.add(Integer.parseInt(phoneNumber));
            phoneBook.put(surname, listNumbers);
        } else {
            phoneBook.get(surname).add(Integer.parseInt(phoneNumber));
        }
    }

    public void get(String surname) {
        if (phoneBook.get(surname) == null) {
            System.out.println("Aбонент " + surname + " не найден!");
        } else {
            System.out.println(surname + ": " + phoneBook.get(surname));
        }
    }

}
