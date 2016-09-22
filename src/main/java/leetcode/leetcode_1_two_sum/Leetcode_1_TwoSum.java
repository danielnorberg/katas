package leetcode.leetcode_1_two_sum;

import com.google.common.primitives.Ints;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class Leetcode_1_TwoSum {

  public static void main(final String... args) {
    assertThat(twoSum(9, 2, 7, 11, 15), contains(0, 1));
  }

  private static List<Integer> twoSum(final int target, final int... nums) {
    return Ints.asList(new Solution().twoSum(nums, target));
  }
}
