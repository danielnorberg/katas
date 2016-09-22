package leetcode.leetcode_33_search_in_rotated_sorted_array;

public class Solution {
  public int search(int[] nums, int target, int a, int b, int l, int r) {
    if (b < a) {
      return -1;
    }

    int i = (b - a) / 2 + a;
    int x = nums[i];

    if (target == x) {
      return i;
    }

    if (l < r) {
      // This is a normal sorted array
      if (target < l || target > r) {
        return -1;
      } else if (target < x) {
        // go left
        return search(nums, target, a,     i - 1, l,           nums[i - 1]);
      } else {
        // go right
        return search(nums, target, i + 1, b,     nums[i + 1], r);
      }
    } else if (l > r) {
      // This is a pivoted sorted array
      if (r < x) {
        // pivot is on the right side -> left side is normal sorted array
        if (l <= target && target < x) {
          // target is within the bounds of the left hand side array -> go left
          return search(nums, target, a, i - 1, l, nums[i - 1]);
        } else {
          // target is outside bounds of the left hand side array -> go right
          return search(nums, target, i + 1, b, nums[i + 1], r);
        }
      } else {
        // pivot is on the left side -> right side is normal sorted array
        if (x < target && target <= r) {
          // target is within the bounds of the right hand side array -> go right
          return search(nums, target, i + 1, b, nums[i + 1], r);
        } else {
          // target is outside the bounds of the right hand side array -> go left
          return search(nums, target, a, i - 1, l, nums[i - 1]);
        }
      }
    } else {
      // We can't be sure on which side the pivot is (if any), so the value might be on either side. Search both left and right.
      int li = search(nums, target, a, i - 1, l, x);
      if (li != -1) {
        return li;
      }
      return search(nums, target, i + 1, b, x, r);
    }
  }

  public int search(int[] nums, int target) {
    if (nums.length == 0) {
      return -1;
    }
    return search(nums, target, 0, nums.length - 1, nums[0], nums[nums.length - 1]);
  }
}