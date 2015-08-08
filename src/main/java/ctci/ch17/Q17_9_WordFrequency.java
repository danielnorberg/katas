package ctci.ch17;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q17_9_WordFrequency {

  private static final class Book {

    private final Map<String, Integer> words;

    public Book(final Map<String, Integer> words) {
      this.words = words;
    }

    public static Book index(final String text) {
      final Map<String, Integer> words = new HashMap<>();
      int i = 0;
      while (i < text.length()) {
        int start = -1;
        while (i < text.length()) {
          final char c = text.charAt(i);
          if (isWord(c)) {
            start = i;
            break;
          }
          i++;
        }
        if (start == -1) {
          break;
        }
        while (i < text.length()) {
          final char c = text.charAt(i);
          if (!isWord(c)) {
            final String word = text.substring(start, i).toLowerCase();
            words.compute(word, (w, count) -> count == null ? 1 : count + 1);
            break;
          }
          i++;
        }
      }
      return new Book(words);
    }

    private static boolean isWord(final char c) {
      if (c >= 'a' && c <= 'z') {
        return true;
      }
      if (c >= 'A' && c <= 'Z') {
        return true;
      }
      return c == '-';
    }

    public int occurrences(final String word) {
      return words.getOrDefault(word.toLowerCase(), 0);
    }
  }

  public static void main(String[] args) {
    final Book book = Book.index(
        "The quick brown fox jumps over the lazy dog's back 1234567890.\n" +
        "The quick brown fox jumps over the lazy dog's back 1234567890.\n" +
        "Unique words.");
    assertThat(book.occurrences("Unique"), is(1));
    assertThat(book.occurrences("brown"), is(2));
    assertThat(book.occurrences("The"), is(4));
    assertThat(book.occurrences("not-present"), is(0));
    assertThat(book.occurrences("1234567890"), is(0));
    assertThat(book.occurrences("."), is(0));
    assertThat(book.occurrences(" "), is(0));
  }
}
