package leetcode.leetcode_8_string_to_integer_atoi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Leetcode_8_StringToIntegerAtoi {

  public static void main(final String... args) {
    assertThat(atoi(""), is(0));
    assertThat(atoi("0"), is(0));
    assertThat(atoi("+0"), is(0));
    assertThat(atoi("-0"), is(0));
    assertThat(atoi("1"), is(1));
    assertThat(atoi(" 1 "), is(1));
    assertThat(atoi(" +1 "), is(1));
    assertThat(atoi(" -1 "), is(-1));
    assertThat(atoi("123"), is(123));
    assertThat(atoi("+123"), is(123));
    assertThat(atoi("-123"), is(-123));
    assertThat(atoi(" 123asdfs"), is(123));
    assertThat(atoi(" +123asdfs"), is(123));
    assertThat(atoi(" -123asdfs"), is(-123));
    assertThat(atoi(" 9223372036854775807asdfs"), is(Integer.MAX_VALUE));
    assertThat(atoi(" +9223372036854775807asdfs"), is(Integer.MAX_VALUE));
    assertThat(atoi(" -9223372036854775807asdfs"), is(Integer.MIN_VALUE));
  }

  private static int atoi(final String s) {
    return new Solution().myAtoi(s);
  }
}
