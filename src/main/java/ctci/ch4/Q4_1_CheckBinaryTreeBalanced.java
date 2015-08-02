package ctci.ch4;

public class Q4_1_CheckBinaryTreeBalanced {

  private static class Node {

    private Node a, b;

    public Node() {
    }

    public Node(final Node a, final Node b) {
      this.a = a;
      this.b = b;
    }

    public int balancedHeight() {
      if (a == null && b == null) {
        return 0;
      }
      if (a == null || b == null) {
        return -1;
      }
      final int aHeight = a.balancedHeight();
      final int bHeight = b.balancedHeight();
      if (aHeight == -1 || bHeight == -1 || Math.abs(aHeight - bHeight) > 1) {
        return -1;
      }
      return Math.max(aHeight, bHeight) + 1;
    }
  }

  public static void main(String[] args) {
    final Node balanced0 = new Node(new Node(), new Node());
    System.out.println(balanced0.balancedHeight());

    final Node balanced1 = new Node(new Node(), new Node(new Node(), new Node()));
    System.out.println(balanced1.balancedHeight());

    final Node unbalanced = new Node(new Node(), new Node(new Node(), new Node(new Node(), new Node())));
    System.out.println(unbalanced.balancedHeight());
  }
}
