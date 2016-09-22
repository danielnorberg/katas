package leetcode.leetcode_2_add_two_numbers;

public class ListNode {

  int val;
  ListNode next;

  ListNode(int x) {
    val = x;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final ListNode listNode = (ListNode) o;

    if (val != listNode.val) {
      return false;
    }
    return next != null ? next.equals(listNode.next) : listNode.next == null;

  }

  @Override
  public int hashCode() {
    int result = val;
    result = 31 * result + (next != null ? next.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder s = new StringBuilder();
    s.append('[');
    ListNode n = this;
    while (true) {
      s.append(n.val);
      if (n.next == null) {
        break;
      }
      s.append(',');
      n = n.next;
    }
    s.append(']');
    return s.toString();
  }

  public static ListNode of(final int... vals) {
    if (vals.length == 0) {
      return null;
    }
    ListNode head = new ListNode(vals[0]);
    ListNode n = head;
    for (int i = 1; i < vals.length; i++) {
      n.next = new ListNode(vals[i]);
      n = n.next;
    }
    return head;
  }
}