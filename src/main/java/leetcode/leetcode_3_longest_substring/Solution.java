package leetcode.leetcode_3_longest_substring;

import java.util.HashMap;
import java.util.Map;

/*
012345
pwwkew

i max start c seenIx  start' cur max' seen'
0   0     0 p      -      0    1   1  {p:0}
1   1     0 w      -      0    2   2  {p:0,w:1}
2   2     0 w      1      2    1   2  {w:2}
3   2     2 k      -      2    2   2  {w:2,k:3}
4   2     2 e      -      2    3   3  {w:2,k:3,e:4}
5   3     2 w      2      3    3   3  {k:3,e:4,w:5}

*/


public class Solution {

  public int lengthOfLongestSubstring(String s) {
    int max = 0;
    int start = 0;
    // Could also use BitSet
    Map<Character, Integer> seen = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      Integer seenIx = seen.get(c);
      if (seenIx != null) {
        for (int j = start; j <= seenIx; j++) {
          seen.remove(s.charAt(j));
        }
        start = seenIx + 1;
      }
      seen.put(c, i);
      int cur = i - start + 1;
      if (cur > max) {
        max = cur;
      }
    }
    return max;
  }
}