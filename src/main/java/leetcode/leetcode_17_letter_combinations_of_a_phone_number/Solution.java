package leetcode.leetcode_17_letter_combinations_of_a_phone_number;

import java.util.ArrayList;
import java.util.List;

public class Solution {

  private static final String[] LETTERS = {
      " ",
      null,
      "abc",
      "def",
      "ghi",
      "jkl",
      "mno",
      "pqrs",
      "tuv",
      "wzyx"
  };

  public List<String> letterCombinations(String digits) {
    List<String> combos = new ArrayList<>();
    if (digits.isEmpty()) {
      return combos;
    }
    for (int i = 0; i < digits.length(); i++) {
      char c = digits.charAt(i);
      if (c < '0' || c > '9' || c == '1') {
        return combos;
      }
    }
    char[] buf = new char[digits.length()];
    letterCombinations(combos, buf, digits, 0);
    return combos;
  }

  private void letterCombinations(List<String> combos, char[] buf, String digits, int ix) {
    if (ix == buf.length) {
      combos.add(new String(buf));
      return;
    }
    char c = digits.charAt(ix);
    int ci = c - '0';
    String letters = LETTERS[ci];
    for (int i = 0; i < letters.length(); i++) {
      buf[ix] = letters.charAt(i);
      letterCombinations(combos, buf, digits, ix + 1);
    }
  }
}