package leetcode.leetcode_16_3sum_closest;

import java.util.Arrays;

public class Solution {

  /**
   * O(n2)
   * TODO: O(nlogn)
   */
  public int threeSumClosest(int[] nums, int target) {
    if (nums.length <= 3) {
      int sum = 0;
      for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
      }
      return sum;
    }
    Arrays.sort(nums);
    int csum = nums[0] + nums[1] + nums[2];
    if (csum == target) {
      return target;
    }
    int cd = Math.abs(target - csum);
    int i = 0;
    int n = nums.length - 2;
    while (true) {
      int a = nums[i];
      int j = i + 1;
      int k = nums.length - 1;
      while (j < k) {
        int sum = a + nums[j] + nums[k];
        if (sum == target) {
          return target;
        }
        int d = target - sum;
        int ad = Math.abs(d);
        if (ad < cd) {
          cd = ad;
          csum = sum;
        }
        while (j < k && nums[j] == nums[j + 1]) {
          j++;
        }
        while (j < k && nums[k] == nums[k - 1]) {
          k--;
        }
        if (d < 0) {
          k--;
        } else {
          j++;
        }
      }
      i++;
      while (i < n && a == nums[i]) {
        i++;
      }
      if (i >= n) {
        break;
      }
    }
    return csum;
  }
}