package leetcode.leetcode_6_zigzag_conversion;

/*

n = 5

P       H
A     S I
Y   I   R
P L     I G
A       N

*/


public class Solution {

  public String convert(String s, int n) {
    StringBuilder b = new StringBuilder();

    for (int row = 0; row < n; row++) {
      for (int i = row, col = 0; i < s.length(); col++) {
        b.append(s.charAt(i));
        if (row == 0 || (row + 1) % n == 0) {
          // Top or bottom row stride
          i += (n + Math.max(0, n - 2));
        } else if (col % 2 == 0) {
          // Vertical column to diagonal column stride
          i += 2 * (n - row - 1);
        } else {
          // Diagonal column to vertical column stride
          i += 2 * row;
        }
      }
    }
    return b.toString();
  }
}