package leetcode.leetcode_2_add_two_numbers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class Leetcode_2_AddTwoNumbers {

  public static void main(final String... args) {
    assertThat(ListNode.of(), is(nullValue()));
    assertThat(ListNode.of(1).toString(), is("[1]"));
    assertThat(ListNode.of(1, 2, 3).toString(), is("[1,2,3]"));
    assertThat(add(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4)), is(ListNode.of(7, 0, 8)));
  }

  private static ListNode add(final ListNode a, final ListNode b) {
    return new Solution().addTwoNumbers(a, b);
  }
}
