package leetcode.leetcode_15_3sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

  public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
      if (i != 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int j = i + 1;
      int k = nums.length - 1;
      int a = nums[i];
      while (j < k) {
        int b = nums[j];
        int c = nums[k];
        int sum = a + b + c;
        if (sum == 0) {
          result.add(Arrays.asList(a, b, c));
          while (j < k && nums[j] == nums[j + 1]) {
            j++;
          }
          j++;
          while (j < k && nums[k] == nums[k - 1]) {
            k--;
          }
          k--;
        } else if (sum < 0) {
          j++;
        } else { // (sum > 0)
          k--;
        }
      }
    }
    return result;
  }
}