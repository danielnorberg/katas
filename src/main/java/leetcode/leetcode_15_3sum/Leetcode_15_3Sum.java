package leetcode.leetcode_15_3sum;

import com.google.common.collect.ImmutableList;

import org.hamcrest.Matchers;

import java.util.List;

import static org.junit.Assert.assertThat;

public class Leetcode_15_3Sum {

  public static void main(final String... args) {
    assertThat(threeSum(-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6), Matchers.is(
        ImmutableList.of(
            ImmutableList.of(-4, -2, 6),
            ImmutableList.of(-4, 0, 4),
            ImmutableList.of(-4, 1, 3),
            ImmutableList.of(-4, 2, 2),
            ImmutableList.of(-2, -2, 4),
            ImmutableList.of(-2, 0, 2)
        )
    ));
  }

  private static List<List<Integer>> threeSum(final int... nums) {
    return new Solution().threeSum(nums);
  }
}
