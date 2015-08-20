package ctci.ch1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q1_4_PercentEncodeSpace {

  public static void main(final String... args) {
    final char[] s = "Mr John Smith    ".toCharArray();
    encodeSpace(s, 13);
    assertThat(new String(s), is("Mr%20John%20Smith"));
  }

  private static void encodeSpace(final char[] s, final int len) {
    int j = s.length - 1;
    for (int i = len - 1; i >= 0; i--) {
      final char c = s[i];
      if (c == ' ') {
        s[j--] = '0';
        s[j--] = '2';
        s[j--] = '%';
      } else {
        s[j--] = c;
      }
    }
  }
}
