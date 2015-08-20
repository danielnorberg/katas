package ctci.ch1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q1_2_ReverseNullTerminatedString {

  public static void main(final String... args) {
    assertThat(reverse("foo\0"), is("oof\0"));
  }

  private static String reverse(final String s) {
    int n = 0;
    while (true) {
      if (s.charAt(n) == '\0') {
        break;
      }
      n++;
    }
    final char[] reversed = new char[n + 1];
    for (int i = 0; i < n; i++) {
      reversed[i] = s.charAt(n - 1 - i);
    }
    reversed[n] = '\0';
    return new String(reversed);
  }
}
