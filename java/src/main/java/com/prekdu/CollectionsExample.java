package com.prekdu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class CollectionsExample {
  /** The number of inputs to be processed by the program. */
  private static final int INPUT_COUNT = 10; // number of inputs

  // Private constructor to prevent instantiation
  private CollectionsExample() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Prompts the user for input and ensures the input is non-empty.
   *
   * @param scanner the scanner to read user input
   * @param index the index of the input prompt
   * @return the valid user input
   */
  private static String getUserInput(final Scanner scanner, final int index) {
    String input;
    while (true) {
      System.out.print("Enter string " + index + ": ");
      input = scanner.nextLine().trim();
      if (!input.isEmpty()) {
        break;
      } else {
        System.out.println("Input cannot be empty.");
      }
    }
    return input;
  }

  /**
   * Adds the input string to the given list.
   *
   * @param list the list to add the string to
   * @param input the input string to add
   */
  private static void addToList(final List<String> list, final String input) {
    list.add(input);
  }

  /**
   * Adds the input string to the given set (to avoid duplicates).
   *
   * @param set the set to add the string to
   * @param input the input string to add
   */
  private static void addToSet(final Set<String> set, final String input) {
    set.add(input);
  }

  /**
   * Displays the contents of the list.
   *
   * @param list the list to display
   */
  private static void displayList(final List<String> list) {
    System.out.println("\nList contents:");
    list.forEach(System.out::println);
  }

  /**
   * Displays the contents of the set.
   *
   * @param set the set to display
   */
  private static void displaySet(final Set<String> set) {
    System.out.println("\nSet contents (no duplicates):");
    set.forEach(System.out::println);
  }

  /**
   * Displays the contents of the HashMap (word frequencies).
   *
   * @param mpp the HashMap to display
   */
  private static void displayHashMap(final HashMap<String, Integer> mpp) {
    System.out.println("\nHashMap contents (word frequencies):");
    mpp.forEach((key, value) -> System.out.println(key + ": " + value));
  }

  /**
   * Main method to run the application.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {

      List<String> list = new ArrayList<>();
      Set<String> set = new HashSet<>();
      HashMap<String, Integer> mpp2 = new HashMap<>();

      for (int i = 1; i <= INPUT_COUNT; i++) {
        String input = getUserInput(scanner, i);
        addToList(list, input);
        addToSet(set, input);
        mpp2.put(input, mpp2.getOrDefault(input, 0) + 1);
      }

      displayList(list);
      displaySet(set);
      displayHashMap(mpp2);

    } catch (Exception e) {
      System.out.println("Error occurred: " + e.getMessage());
    }
  }
}
