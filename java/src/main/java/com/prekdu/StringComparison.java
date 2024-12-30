package com.prekdu;

import java.util.Scanner;

/** Utility class to compare two strings and print their details. */
public final class StringComparison {

  private StringComparison() {
    // Private constructor to prevent instantiation
    throw new UnsupportedOperationException("This is a utility class");
  }

  /**
   * Method to get string input from the user.
   *
   * @param p the prompt to display to the user
   * @param sc the scanner object for input
   * @return the user input
   */
  private static String getStringInput(final String p, final Scanner sc) {
    System.out.println(p);
    String input = sc.nextLine();

    // Validate the input
    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty.");
    }
    return input;
  }

  /**
   * Method to compare the lengths of two strings and print the result.
   *
   * @param str1 the first string
   * @param str2 the second string
   */
  private static void compareLengths(final String str1, final String str2) {
    System.out.println("Length of first string: " + str1.length());
    System.out.println("Length of second string: " + str2.length());

    if (str1.length() == str2.length()) {
      System.out.println("The lengths of the two strings match.");
    } else {
      System.out.println("The lengths of the two strings do not match.");
    }
  }

  /**
   * Method to compare if the two strings are the same.
   *
   * @param str1 the first string
   * @param str2 the second string
   */
  private static void compareStrings(final String str1, final String str2) {
    if (str1.equals(str2)) {
      System.out.println("The two strings are the same.");
    } else {
      System.out.println("The two strings are not the same.");
    }
  }

  /**
   * Main method to execute the program.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    // Use try-with-resources to ensure Scanner is closed automatically
    try (Scanner scanner = new Scanner(System.in)) {
      // Get input strings from the user
      final String str1 = getStringInput("Enter the first string:", scanner);
      final String str2 = getStringInput("Enter the second string:", scanner);

      // Compare lengths
      compareLengths(str1, str2);

      // Compare the strings themselves
      compareStrings(str1, str2);

    } catch (IllegalArgumentException e) {
      System.err.println("Error: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
  }
}
