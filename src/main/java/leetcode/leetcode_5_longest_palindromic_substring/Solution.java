package leetcode.leetcode_5_longest_palindromic_substring;

public class Solution {
  private boolean isPalindrome(String s, int a, int b) {
    while (a < b) {
      if (s.charAt(a++) != s.charAt(b--)) {
        return false;
      }
    }
    return true;
  }

  public String longestPalindrome(String s) {
    return longestPalindromeQuadratic(s);
  }

  // O(n2)
  public String longestPalindromeQuadratic(String s) {
    if (s.isEmpty()) {
      return s;
    }
    int start = 0;
    int end = 0;
    int max = 0;
    for (int i = 0; i < s.length(); i++) {
      for (int j = 0; j < 2; j++) {
        int n;
        int a = i;
        int b = i + j;
        while (a >= 0 && b < s.length() && s.charAt(a) == s.charAt(b)) {
          n = b - a + 1;
          if (n > max) {
            max = n;
            start = a;
            end = b;
          }
          a--;
          b++;
        }
      }
    }
    return s.substring(start, end + 1);
  }

  // O(n3)
  public String longestPalindromeQubic(String s) {
    int start = 0;
    int end = 0;
    int max = 0;
    for (int i = 0; i < s.length(); i++) {
      for (int j = i; j < s.length(); j++) {
        int n = j - i + 1;
        if (n > max && isPalindrome(s, i, j)) {
          max = n;
          start = i;
          end = j;
        }
      }
    }
    return s.substring(start, end + 1);
  }
}