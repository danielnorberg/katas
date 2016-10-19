package leetcode.leetcode_18_4sum;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class Main {

  public static void main(final String... args) {
    assertThat(fourSum(3, -1, 2, 2, -5, 0, -1, 4), contains(
        contains(-5, 2, 2, 4),
        contains(-1, 0, 2, 2)));
  }

  static List<List<Integer>> fourSum(int target, int... nums) {
    return new Solution().fourSum(nums, target);
  }

}
