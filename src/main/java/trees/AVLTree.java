package trees;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Integer.max;

public class AVLTree<T> {

  private final Comparator<T> comparator;

  Node<T> root;

  public AVLTree(final Comparator<T> comparator) {
    this.comparator = comparator;
  }

  public void insert(T value) {
    checkNotNull(value);
    if (root == null) {
      root = new Node<>(value);
    } else {
      root = insert(root, value);
    }
  }

  private Node<T> insert(final Node<T> node, final T value) {
    if (value.equals(node.value)) {
      return node;
    }
    if (comparator.compare(value, node.value) < 0) {
      if (node.left == null) {
        node.left = new Node<>(value);
      } else {
        node.left = insert(node.left, value);
      }
      // Left
    } else {
      // Right
      if (node.right == null) {
        node.right = new Node<>(value);
      } else {
        node.right = insert(node.right, value);
      }
    }

    update(node);

    if (node.balance > 1) {
      // Leaning left
      //       3
      //    2     a
      //  1   b
      // c d
      // Rotate 3 right
      //       2
      //    1     3
      //  c   d  b  a

      // Leaning right
      //       3
      //    2     a
      //  c   1
      //     d b
      // Rotate 2 left
      //       3
      //    1     a
      //  2   b
      // c d
      // Rotate 3 right

      if (node.left.balance == -1) {
        node.left = rotateLeft(node.left);
      }

      return rotateRight(node);
    } else if (node.balance < -1) {

      if (node.left.balance == 1) {
        node.right = rotateRight(node.right);
      }

      return rotateRight(node);
    } else {
      return node;
    }
  }

  private void update(final Node<T> node) {
    node.height = max(height(node.left), height(node.right));
    node.balance = height(node.left) - height(node.right);
  }

  private int height(final Node<T> node) {
    return node == null ? 0 : node.height + 1;
  }

  private Node<T> rotateLeft(final Node<T> node) {
    //    a              c
    //  b   c    =>    a  e
    //     d e        b d

    final Node<T> a = node;
    final Node<T> c = a.right;
    final Node<T> d = c.left;

    c.left = a;
    a.right = d;

    update(a);
    update(c);

    return c;
  }

  private Node<T> rotateRight(final Node<T> node) {
    //    a             b
    //  b  c     =>   d   a
    // d e               e c

    final Node<T> a = node;
    final Node<T> b = a.left;
    final Node<T> e = b.right;

    b.right = a;
    a.left = e;

    update(a);
    update(b);

    return b;
  }

  static class Node<T> {

    Node<T> left;
    Node<T> right;
    int height;
    int balance;
    T value;

    public Node(final T value) {
      this.value = value;
    }
  }
}
