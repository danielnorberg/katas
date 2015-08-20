package ctci.ch1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q1_5_RLE {

  public static void main(final String... args) {
    assertThat(rle("aaabb"), is("a3b2"));
    assertThat(rle("aabb"), is("aabb"));
  }

  private static String rle(final String s) {
    if (s.length() < 3) {
      return s;
    }
    final StringBuilder b = new StringBuilder();
    int i = 1;
    int n = 1;
    char rl = s.charAt(0);
    while (i < s.length()) {
      final char c = s.charAt(i);
      if (c != rl) {
        b.append(rl);
        b.append(n);
        rl = c;
        n = 1;
      } else {
        n++;
      }
      i++;
    }
    b.append(rl);
    b.append(n);
    if (b.length() >= s.length()) {
      return s;
    }
    return b.toString();
  }
}
