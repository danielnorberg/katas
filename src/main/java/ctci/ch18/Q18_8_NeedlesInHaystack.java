package ctci.ch18;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_8_NeedlesInHaystack {

  private static class Node {

    private final Map<Character, Node> nodes = new HashMap<>();

    private List<Integer> indices = new ArrayList<>();
    private boolean terminal;

    public void insert(final int index, final String word) {
      insert(index, word, 0);
    }

    private void insert(final int index, final String word, final int i) {
      indices.add(index);
      if (word.length() == i) {
        terminal = true;
        return;
      }
      final char c = word.charAt(i);
      nodes.computeIfAbsent(c, k -> new Node()).insert(index, word, i + 1);
    }

    public int searchPrefix(final String s, final int i) {
      if (terminal) {
        return i;
      }
      final char c = s.charAt(i);
      final Node next = nodes.get(c);
      if (next == null) {
        return -1;
      }
      return next.searchPrefix(s, i + 1);
    }

    public Node search(final String s) {
      return search(s, 0);
    }

    private Node search(final String s, final int i) {
      if (i == s.length()) {
        return this;
      }
      final char c = s.charAt(i);
      final Node next = nodes.get(c);
      if (next == null) {
        return null;
      }
      return next.search(s, i + 1);
    }
  }

  public static void main(final String... args) {
    assertThat(
        searchOptimizedForManyWords("ahaystackofwordsthatwe'lllookfor", asList("stack", "words", "notpresent")),
        is(ImmutableMap.of("stack", 4, "words", 11)));
    assertThat(
        searchOptimizedForLargeHaystack("ahaystackofwordsstackwithhay", asList("stack", "words", "notpresent")),
        is(ImmutableMap.of("stack", asList(4, 16), "words", asList(11))));
  }

  private static Map<String, List<Integer>> searchOptimizedForLargeHaystack(final String haystack,
                                                                            final List<String> needles) {
    final Map<String, List<Integer>> index = new HashMap<>();
    final Node trie = new Node();
    for (int i = 0; i < haystack.length() - 1; i++) {
      trie.insert(i, haystack, i);
    }
    for (final String needle : needles) {
      final Node node = trie.search(needle);
      if (node != null) {
        index.put(needle, node.indices);
      }
    }
    return index;
  }

  private static Map<String, Integer> searchOptimizedForManyWords(final String haystack,
                                                                  final List<String> needles) {
    final Map<String, Integer> indices = new HashMap<>();
    final Node trie = new Node();
    for (final String word : needles) {
      trie.insert(0, word);
    }
    for (int i = 0; i < haystack.length(); i++) {
      final int end = trie.searchPrefix(haystack, i);
      if (end != -1) {
        final String word = haystack.substring(i, end);
        final int index = i;
        indices.computeIfAbsent(word, k -> index);
      }
    }
    return indices;
  }

}
