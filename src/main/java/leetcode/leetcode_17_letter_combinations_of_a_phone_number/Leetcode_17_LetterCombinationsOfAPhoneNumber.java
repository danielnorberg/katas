package leetcode.leetcode_17_letter_combinations_of_a_phone_number;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Leetcode_17_LetterCombinationsOfAPhoneNumber {

  public static void main(final String... args) {
    assertThat(letterCombinations(""), is(empty()));
    assertThat(letterCombinations("23"), containsInAnyOrder("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"));
  }

  private static List<String> letterCombinations(final String digits) {
    return new Solution().letterCombinations(digits);
  }
}
