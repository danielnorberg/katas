package ctci.ch4;

import java.util.ArrayList;
import java.util.List;

public class Q4_3_MinimalBST {

  private static class Node {

    private int value;
    private Node a, b;

    public Node(final int value) {
      this.value = value;
    }

    public Node(final int value, final Node a, final Node b) {
      this.value = value;
      this.a = a;
      this.b = b;
    }

    @Override
    public String toString() {
      final StringBuilder s = new StringBuilder();
      print(0, s);
      return s.toString();
    }

    private void print(final int indent, final StringBuilder s) {
      for (int i = 0; i < indent; i++) {
        s.append(' ');
      }
      s.append(value);
      if (a != null || b != null) {
        if (a != null) {
          s.append('\n');
          a.print(indent + 1, s);
        }
        if (b != null) {
          s.append('\n');
          b.print(indent + 1, s);
        }
      }
    }
  }

  public static void main(String[] args) {
    final List<Integer> values = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      values.add(i);
      final Node bst = minimalBST(values);
      System.out.println(values);
      System.out.println(bst);
    }
  }

  private static Node minimalBST(final List<Integer> values) {
    switch (values.size()) {
      case 0:
        return null;
      default:
        final int mid = values.size() / 2;
        final int val = values.get(mid);
        return new Node(val, minimalBST(values.subList(0, mid)), minimalBST(values.subList(mid + 1, values.size())));
    }
  }


}
