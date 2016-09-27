package leetcode.leetcode_6_zigzag_conversion;

import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

public class Leetcode_6_ZigzagConversion {

  public static void main(final String... args) {
    assertThat(zigzag("", 1), Matchers.is(""));
    assertThat(zigzag("A", 1), Matchers.is("A"));
    assertThat(zigzag("A", 2), Matchers.is("A"));
    assertThat(zigzag("AB", 1), Matchers.is("AB"));
    assertThat(zigzag("AB", 2), Matchers.is("AB"));
    assertThat(zigzag("ABC", 2), Matchers.is("ACB"));
    assertThat(zigzag("ABC", 1), Matchers.is("ABC"));
    assertThat(zigzag("ABCD", 2), Matchers.is("ACBD"));
    assertThat(zigzag("PAYPALISHIRING", 3), Matchers.is("PAHNAPLSIIGYIR"));
  }

  private static String zigzag(final String s, final int rows) {
    return new Solution().convert(s, rows);
  }
}
