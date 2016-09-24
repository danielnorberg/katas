package leetcode.leetcode_4_median_of_two_sorted_arrays;

/*
nums1 = [1, 3]
nums2 = [2]

len = 3;
n = 2;
even = false;

i p v i1 i2 p' v' i1' i2'
0 0 0  0  0 0  1   1   0
1 0 1  1  0 1  2   1   1

median = 2

*/

/*
nums1 = [1, 2]
nums2 = [3, 4]

len = 4;
even = true;
n = 3;

i p v i1 i2 p' v' i1' i2'
0 0 0  0  0 0  1   1   0
1 0 1  1  0 1  2   2   0
2 1 2  2  0 2  3   2   1

median = (2 + 3) / 2.0 = 5 / 2.0 = 2.5




*/

public class Solution {

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {

    if (nums1.length == 0 && nums2.length == 0) {
      return 0;
    }

    int len = nums1.length + nums2.length;
    int n = (len + 1) / 2;
    boolean even = (len % 2) == 0;
    if (even) {
      n += 1;
    }
    int p = 0;
    int v = 0;
    int i1 = 0;
    int i2 = 0;

    for (int i = 0; i < n; i++) {
      p = v;
      if (i1 >= nums1.length) {
        v = nums2[i2++];
      } else if (i2 >= nums2.length) {
        v = nums1[i1++];
      } else if (nums1[i1] < nums2[i2]) {
        v = nums1[i1++];
      } else {
        v = nums2[i2++];
      }
    }

    if (even) {
      return (p + v) / 2.0;
    } else {
      return v;
    }
  }
}