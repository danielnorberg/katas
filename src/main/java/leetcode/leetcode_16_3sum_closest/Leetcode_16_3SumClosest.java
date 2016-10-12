package leetcode.leetcode_16_3sum_closest;

import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

public class Leetcode_16_3SumClosest {

  public static void main(final String... args) {
    assertThat(threeSumClosest(1, -1, 2, 1, -4), Matchers.is(2));
  }

  private static int threeSumClosest(final int target, final int... nums) {
    return new Solution().threeSumClosest(nums, target);
  }
}
