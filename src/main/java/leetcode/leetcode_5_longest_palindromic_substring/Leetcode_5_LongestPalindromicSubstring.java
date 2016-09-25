package leetcode.leetcode_5_longest_palindromic_substring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Leetcode_5_LongestPalindromicSubstring {

  public static void main(final String... args) {
    assertThat(longestPalindromicSubstring(""), is(""));
    assertThat(longestPalindromicSubstring("a"), is("a"));
    assertThat(longestPalindromicSubstring("aba"), is("aba"));
    assertThat(longestPalindromicSubstring("abba"), is("abba"));
    assertThat(longestPalindromicSubstring("abbaabbbaa"), is("aabbbaa"));
    assertThat(longestPalindromicSubstring("aabbbaabbaa"), is("aabbbaa"));
    assertThat(longestPalindromicSubstring("aabbbaabbbaa"), is("aabbbaabbbaa"));
  }

  private static String longestPalindromicSubstring(final String s) {
    return new Solution().longestPalindrome(s);
  }
}
