package leetcode.leetcode_8_string_to_integer_atoi;

public class Solution {

  public int myAtoi(String s) {
    long r = 0;
    int i = 0;
    char c;

    // TODO: handle unicode?

    // Skip whitespace
    while (true) {
      if (i >= s.length()) {
        return 0;
      }
      c = s.charAt(i);
      if (!isWhitespace(c)) {
        break;
      }
      i++;
    }

    // Check for sign
    int sign;
    if (c == '-') {
      sign = -1;
      i++;
    } else if (c == '+') {
      sign = 1;
      i++;
    } else {
      sign = 1;
    }

    // Parse number
    while (true) {
      if (i >= s.length()) {
        break;
      }
      c = s.charAt(i);
      if (!isDigit(c)) {
        break;
      }
      r *= 10;
      r += digit(c);
      i++;

      // Check over & underflow
      if (r > Integer.MAX_VALUE) {
        if (sign == 1) {
          return Integer.MAX_VALUE;
        } else {
          return Integer.MIN_VALUE;
        }
      }
    }

    r *= sign;

    if (r < Integer.MIN_VALUE) {
      return Integer.MIN_VALUE;
    }

    return (int) r;
  }

  private static int digit(char c) {
    assert isDigit(c);
    return c - '0';
  }

  private static boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private static boolean isWhitespace(char c) {
    return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f';
  }
}