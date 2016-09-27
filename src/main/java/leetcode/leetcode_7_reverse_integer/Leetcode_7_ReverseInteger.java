package leetcode.leetcode_7_reverse_integer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Leetcode_7_ReverseInteger {

  public static void main(final String... args) {
    assertThat(reverse(0), is(0));
    assertThat(reverse(1), is(1));
    assertThat(reverse(-1), is(-1));
    assertThat(reverse(321), is(123));
    assertThat(reverse(-321), is(-123));
    assertThat(reverse(1534236469), is(0));
  }

  private static int reverse(final int x) {
    return new Solution().reverse(x);
  }
}
