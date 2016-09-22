package leetcode.leetcode_2_add_two_numbers;

public class Solution {
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    return addTwoNumbers(l1, l2, 0);
  }

  private ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
    if (l1 == null && l2 == null && carry == 0) {
      return null;
    }

    ListNode node = new ListNode(carry);

    ListNode n1 = null;
    if (l1 != null) {
      n1 = l1.next;
      node.val += l1.val;
    }

    ListNode n2 = null;
    if (l2 != null) {
      n2 = l2.next;
      node.val += l2.val;
    }

    int nextCarry = 0;
    if (node.val >= 10) {
      node.val -= 10;
      nextCarry = 1;
    }

    node.next = addTwoNumbers(n1, n2, nextCarry);
    return node;
  }
}