package com.prekdu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/** Utility class to process CSV files and display word frequencies. */
public final class CSVWordFrequency {

  /** The number of top words to display. */
  private static final int TOP_N = 3;

  // Private constructor to prevent instantiation
  private CSVWordFrequency() {
    throw new UnsupportedOperationException("This is a utility class");
  }

  /**
   * Reads the CSV file and returns its content as a single string.
   *
   * @param filePath the path of the CSV file
   * @return the content of the file as a string
   * @throws IOException if an error occurs while reading the file
   */
  private static String readFile(final String filePath) throws IOException {
    StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line).append(" ");
      }
    }
    return content.toString();
  }

  /**
   * Counts word frequencies in the given content.
   *
   * @param content the content to process
   * @return a HashMap of word counts
   */
  private static HashMap<String, Integer> countWords(final String content) {
    // Step 1: Remove unwanted characters
    String temp = content.replaceAll("[^a-zA-Z0-9\\s,]", "");

    // Step 2: Convert the sanitized content to lowercase
    String lowerContent = temp.toLowerCase(Locale.ENGLISH);

    // Step 3: Split the content into words
    String[] words = lowerContent.split("[,\\s]+");

    HashMap<String, Integer> wordCounts = new HashMap<>();
    for (String word : words) {
      if (!word.isBlank()) {
        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
      }
    }
    return wordCounts;
  }

  /**
   * Gets the top N most frequent words.
   *
   * @param wc the word counts HashMap
   * @param n the number of top words to retrieve
   * @return a list of the top N words sorted by frequency
   */
  private static List<HashMap.Entry<String, Integer>> getTopWords(
      final HashMap<String, Integer> wc, final int n) {
    List<HashMap.Entry<String, Integer>> list = new ArrayList<>(wc.entrySet());

    list.sort(
        (a, b) -> {
          if (b.getValue().equals(a.getValue())) {
            return a.getKey().compareTo(b.getKey());
          }
          return b.getValue() - a.getValue();
        });

    return list.subList(0, Math.min(n, list.size()));
  }

  /**
   * Main method to run the application.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    final String filePath =
        "/Users/yashagarwal/Documents/work/github_repo/"
            + "Kickdrum/pre-kdu-training/java/src/main/resources/input.csv";

    try {
      String content = readFile(filePath);
      HashMap<String, Integer> wc = countWords(content);
      List<HashMap.Entry<String, Integer>> topWords = getTopWords(wc, TOP_N);

      System.out.println("Top " + TOP_N + " repeated words:");
      for (HashMap.Entry<String, Integer> entry : topWords) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
    } catch (IOException e) {
      System.out.println("Error: Unable to read the file. " + e.getMessage());
    }
  }
}
