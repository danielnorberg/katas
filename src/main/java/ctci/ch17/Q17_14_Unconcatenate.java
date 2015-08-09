package ctci.ch17;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class Q17_14_Unconcatenate {

  private static final Set<String> DICTIONARY = ImmutableSet.of("looked", "just", "like", "her", "brother");

  private static final Node TRIE = trie(DICTIONARY);

  private static Node trie(final Collection<String> words) {
    final Node trie = new Node(' ');
    for (final String word : words) {
      trie.insert(word, 0);
    }
    return trie;
  }

  public static void main(String[] args) {
    assertThat(unconcatenate("jesslookedjustliketimherbrother"),
               contains("jess", "looked", "just", "like", "tim", "her", "brother"));
  }

  private static List<String> unconcatenate(final String input) {
    StringBuilder unparsed = new StringBuilder();
    final List<String> words = new ArrayList<>();
    int i = 0;
    while (i < input.length()) {
      char c0 = input.charAt(i);
      Node node = TRIE.nodes.get(c0);
      int j = i + 1;
      boolean match = false;
      while (node != null) {
        final Node next;
        if (j < input.length()) {
          final char c = input.charAt(j);
          next = node.nodes.get(c);
        } else {
          next = null;
        }
        if (next == null) {
          if (!node.leaf) {
            break;
          }
          match = true;
          if (unparsed.length() > 0) {
            words.add(unparsed.toString());
            unparsed = new StringBuilder();
          }
          final String word = input.substring(i, j);
          words.add(word);
          i = j;
          break;
        }
        node = next;
        j++;
      }
      if (!match) {
        unparsed.append(c0);
        i++;
      }
    }
    if (unparsed.length() > 0) {
      words.add(unparsed.toString());
    }
    return words;
  }

  private static int search(final Map<Character, Node> trie, final String input, final int i, final int j) {
    final char c = input.charAt(i);
    final Node node = trie.get(c);
    if (node == null) {
      return j;
    }
    return search(node.nodes, input, i + 1, j + 1);
  }

  private static class Node {

    private boolean leaf;
    private final char c;
    private final Map<Character, Node> nodes = new HashMap<>();

    private Node(final char c) {
      this.c = c;
    }

    public void insert(final String word, final int i) {
      if (i >= word.length()) {
        leaf = true;
        return;
      }
      final char c = word.charAt(i);
      nodes.computeIfAbsent(c, Node::new).insert(word, i + 1);
    }

    @Override
    public String toString() {
      return String.valueOf(c);
    }
  }
}
