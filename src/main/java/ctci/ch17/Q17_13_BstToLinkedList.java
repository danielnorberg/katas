package ctci.ch17;

public class Q17_13_BstToLinkedList {

  public static class BiNode {

    public BiNode node1, node2;
    public int data;

    public BiNode(final int data, final BiNode node1, final BiNode node2) {
      this.node1 = node1;
      this.node2 = node2;
      this.data = data;
    }

    public BiNode(final int data) {
      this.data = data;
    }

    public BiNode trans() {
      return trans(null, null);
    }

    public BiNode trans(BiNode prev, final BiNode next) {
      BiNode head = this;
      final BiNode node1 = this.node1;
      final BiNode node2 = this.node2;
      if (node1 != null) {
        head = node1.trans(prev, this);
      } else {
        this.node1 = prev;
        if (prev != null) {
          prev.node2 = this;
        }
      }
      if (node2 != null) {
        node2.trans(this, next);
      } else {
        this.node2 = next;
        if (next != null) {
          next.node1 = this;
        }
      }
      return head;
    }

    public void printList() {
      System.out.println(data);
      if (node2 != null) {
        node2.printList();
      }
    }

    @Override
    public String toString() {
      return String.valueOf(data);
    }
  }

  public static void main(String[] args) {

    //           5
    //     2            7
    //  1     3      6     9
    // 0 _   _ 4    _ _   8 10

    final BiNode bst = new BiNode(
        5,
        new BiNode(
            2,
            new BiNode(
                1,
                new BiNode(
                    0),
                null),
            new BiNode(
                3,
                null,
                new BiNode(
                    4))),
        new BiNode(
            7,
            new BiNode(
                6),
            new BiNode(
                9,
                new BiNode(8),
                new BiNode(10))));

    final BiNode list = bst.trans();

    list.printList();
  }
}
