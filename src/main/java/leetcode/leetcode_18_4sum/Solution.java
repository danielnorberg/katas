package leetcode.leetcode_18_4sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

  public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums.length < 4) {
      return res;
    }
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 3; i++) {
      for (int j = i + 1; j < nums.length - 2; j++) {
        int l = j + 1;
        int r = nums.length - 1;
        while (l < r) {
          int sum = nums[i] + nums[j] + nums[l] + nums[r];
          if (sum == target) {
            res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
            while (l < r && nums[l] == nums[l + 1]) { l++; }
            while (l < r && nums[r] == nums[r - 1]) { r--; }
            l++;
            r--;
          } else if (sum < target) {
            l++;
          } else { // sum > target
            r--;
          }
        }
        while (j < nums.length - 2 && nums[j] == nums[j + 1]) { j++; }
      }
      while (i < nums.length - 3 && nums[i] == nums[i + 1]) { i++; }
    }
    return res;
  }
}