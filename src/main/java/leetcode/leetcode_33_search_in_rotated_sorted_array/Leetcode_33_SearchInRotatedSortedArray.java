package leetcode.leetcode_33_search_in_rotated_sorted_array;

import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

public class Leetcode_33_SearchInRotatedSortedArray {

  public static void main(final String... args) {
    assertThat(search(0, 1, 3), Matchers.is(-1));
    assertThat(search(5, 5, 1, 3), Matchers.is(0));
  }

  private static int search(final int needle, final int... nums) {
    Solution solution = new Solution();
    return solution.search(nums, needle);
  }
}
