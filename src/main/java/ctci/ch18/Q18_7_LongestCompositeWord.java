package ctci.ch18;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_7_LongestCompositeWord {

  private static class Node {

    private final Map<Character, Node> nodes = new HashMap<>();

    private boolean terminal;

    public void insert(String word) {
      insert(word, 0);
    }

    private void insert(final String word, final int i) {
      if (word.length() == i) {
        terminal = true;
        return;
      }
      final char c = word.charAt(i);
      nodes.computeIfAbsent(c, k -> new Node()).insert(word, i + 1);
    }

    public boolean isComposite(final String word) {
      return isComposite(word, 0, this, false);
    }

    private boolean isComposite(final String word, final int i, final Node root, final boolean composite) {
      if (word.length() == i) {
        return terminal && composite;
      }
      final char c = word.charAt(i);
      final Node next = nodes.get(c);
      return terminal && root.isComposite(word, i, root, true) ||
             next != null && next.isComposite(word, i + 1, root, composite);
    }
  }

  public static void main(final String... args) {
    assertThat(longestCompositeWord("cat", "banana", "dog", "nana", "walk", "walker", "dogwalker", "catdogwalker"),
               is("catdogwalker"));
  }

  private static String longestCompositeWord(final String... words) {
    final Node trie = new Node();
    for (final String word : words) {
      trie.insert(word);
    }
    int maxLen = -1;
    String maxWord = null;
    for (final String word : words) {
      if (word.length() < maxLen) {
        continue;
      }
      if (trie.isComposite(word)) {
        maxLen = word.length();
        maxWord = word;
      }
    }
    return maxWord;
  }
}
