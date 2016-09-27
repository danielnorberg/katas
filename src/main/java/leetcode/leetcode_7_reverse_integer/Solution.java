package leetcode.leetcode_7_reverse_integer;

/*

x    r  r'   d  r''  x'
123  0  0    3  3    12
12   3  30   2  32   1
1    32 320  1  321  0

*/

public class Solution {

  public int reverse(int x) {
    long r = 0;
    int sign = (x >= 0) ? 1 : -1;
    x *= sign;
    while (x > 0) {
      r *= 10;
      long d = x % 10;
      r += d;
      x /= 10;
    }
    // XXX: Might be preferrable to throw an exception on overflow
    if (r > Integer.MAX_VALUE || r * sign < Integer.MIN_VALUE) {
      return 0;
    }
    return (int) (r * sign);
  }
}