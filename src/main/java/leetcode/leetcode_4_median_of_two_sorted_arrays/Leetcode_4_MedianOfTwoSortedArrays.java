package leetcode.leetcode_4_median_of_two_sorted_arrays;

import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

public class Leetcode_4_MedianOfTwoSortedArrays {

  public static void main(final String... args) {
    assertThat(findMedianSortedArrays(nums(), nums()), Matchers.is(0.0d));
    assertThat(findMedianSortedArrays(nums(1), nums()), Matchers.is(1.0d));
    assertThat(findMedianSortedArrays(nums(), nums(1)), Matchers.is(1.0d));
    assertThat(findMedianSortedArrays(nums(1), nums(2)), Matchers.is(1.5d));
    assertThat(findMedianSortedArrays(nums(1, 3), nums(2)), Matchers.is(2.0d));
    assertThat(findMedianSortedArrays(nums(1, 2), nums(3, 4)), Matchers.is(2.5d));
  }

  private static double findMedianSortedArrays(final int[] nums1, final int[] nums2) {
    return new Solution().findMedianSortedArrays(nums1, nums2);
  }

  private static int[] nums(final int... nums) {
    return nums;
  }
}
