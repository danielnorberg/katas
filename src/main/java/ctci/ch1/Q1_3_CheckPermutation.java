package ctci.ch1;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q1_3_CheckPermutation {

  public static void main(final String... args) {
    assertThat(isPermutation("foobar", "barfoo"), is(true));
    assertThat(isPermutation("foobaz", "barfoo"), is(false));
  }

  private static boolean isPermutation(final String s1, final String s2) {
    return sorted(s1).equals(sorted(s2));
  }

  private static String sorted(final String s) {
    final char[] chars = s.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }
}
