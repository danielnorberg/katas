package leetcode.leetcode_3_longest_substring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Leetcode_3_LongestSubstring {

  public static void main(final String... args) {
    assertThat(lengthOfLongestSubstring("pwwkew"), is(3));
    assertThat(lengthOfLongestSubstring("abcabcbb"), is(3));
    assertThat(lengthOfLongestSubstring("bbbbb"), is(1));
    assertThat(lengthOfLongestSubstring("b"), is(1));
    assertThat(lengthOfLongestSubstring(""), is(0));
  }

  private static int lengthOfLongestSubstring(final String s) {
    return new Solution().lengthOfLongestSubstring(s);
  }
}
