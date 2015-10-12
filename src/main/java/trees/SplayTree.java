package trees;

import java.util.Comparator;
import java.util.Objects;

public class SplayTree<T, U> {

  static class Node<T, U> {

    final T key;
    U value;

    Node<T, U> parent;
    Node<T, U> left;
    Node<T, U> right;

    Node(final T key, final U value) {
      this.key = key;
      this.value = value;
    }

    Node(final T key, final Node<T, U> parent) {
      this.key = key;
      this.parent = parent;
    }

    static <T, U> Node<T, U> of(final T key) {
      return of(key, (U) null);
    }

    static <T, U> Node<T, U> of(final T key, final U value) {
      return new Node<>(key, value);
    }

    void children(final Node<T, U> left, final Node<T, U> right) {
      left(left);
      right(right);
    }

    void left(final Node<T, U> node) {
      left = node;
      if (left != null) {
        left.parent = this;
      }
    }

    void right(final Node<T, U> node) {
      right = node;
      if (right != null) {
        right.parent = this;
      }
    }

    Node<T, U> cloneTree() {
      return cloneTree(null);
    }

    Node<T, U> cloneTree(final Node<T, U> p) {
      final Node<T, U> n = new Node<T, U>(key, value);
      n.parent = p;
      n.left = (this.left == null) ? null : this.left.cloneTree(n);
      n.right = (this.right == null) ? null : this.right.cloneTree(n);
      return n;
    }

    static <T, U> boolean treeEquals(final Node<T, U> a, final Node<T, U> b) {
      if (a == null && b == null) {
        return true;
      }
      if (!Objects.equals(a, b)) {
        return false;
      }
      if (!Objects.equals(a.parent, b.parent)) {
        return false;
      }
      if (!treeEquals(a.left, b.left)) {
        return false;
      }
      if (!treeEquals(a.right, b.right)) {
        return false;
      }
      return true;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      final Node<?, ?> node = (Node<?, ?>) o;

      return !(key != null ? !key.equals(node.key) : node.key != null);

    }

    @Override
    public int hashCode() {
      return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
      return String.valueOf(key);
    }
  }

  private final Comparator<T> comparator;
  Node<T, U> root;

  public SplayTree(final Comparator<T> comparator) {
    this(comparator, null);
  }

  SplayTree(final Comparator<T> comparator, final Node<T, U> root) {
    this.comparator = comparator;
    this.root = root;
  }

  public void insert(T key, U value) {
    final Node<T, U> n = new Node<>(key, value);
    if (root == null) {
      root = n;
      return;
    }

    root = insert(root, n, comparator);
  }

  public Node<T, U> find(final T key) {
    return find(key, root, comparator);
  }

  static <T, U> Node<T, U> insert(final Node<T, U> root, final Node<T, U> n, final Comparator<T> comparator) {

    // Traverse tree and find place for new node
    Node<T, U> p = root;
    while (true) {
      if (comparator.compare(n.key, p.key) < 0) {
        // Left
        if (p.left == null) {
          p.left = n;
          break;
        }
        p = p.left;
      } else {
        // Right
        if (p.right == null) {
          p.right = n;
          break;
        }
        p = p.right;
      }
    }

    // Set the parent pointer
    n.parent = p;

    // Splay the node to the root
    splay(n);

    return n;
  }

  static <T, U> Node<T, U> find(final T key, Node<T, U> n, final Comparator<T> comparator) {

    while (n != null) {
      final int compare = comparator.compare(key, n.key);
      if (compare == 0) {
        break;
      }
      if (compare < 0) {
        n = n.left;
      } else {
        n = n.right;
      }
    }

    // Splay the node to the root
    if (n != null) {
      splay(n);
    }

    return n;
  }


  static <T, U> void splay(Node<T, U> x) {
    // Is x root?
    final Node<T, U> p = x.parent;
    if (p == null) {
      return;
    }

    // Is parent root?
    final Node<T, U> g = p.parent;
    if (g == null) {
      zig(x);
      return;
    }

    // Rotate up x to take the place of the grandparent
    zigZag(x);

    // Recurse until x is root
    splay(x);
  }

  static <T, U> void zigZag(final Node<T, U> x) {
    assert x != null;
    final Node<T, U> p = x.parent;
    assert p != null;
    final Node<T, U> g = p.parent;
    assert g != null;
    final Node<T, U> gg = g.parent;

    if (x == p.left) {
      if (p == g.left) {
        zigZigRight(x);
      } else {
        zigZagRightLeft(x);
      }
    } else {
      if (p == g.right) {
        zigZigLeft(x);
      } else {
        zigZagLeftRight(x);
      }
    }
  }

  static <T, U> void zigZagLeftRight(final Node<T, U> x) {
    assert x != null;
    assert x.parent != null;
    assert x.parent.parent != null;
    assert x == x.parent.right;
    assert x.parent == x.parent.parent.left;

    // Rotate p left and then g right
    //        g                  g              x
    //     p     d    ->      x     d   ->   p     g
    //   a   x              p  c            a b   c d
    //      b c            a b
    final Node<T, U> p = x.parent;
    final Node<T, U> g = p.parent;
    rotateLeft(p);
    rotateRight(g);
  }

  static <T, U> void zigZagRightLeft(final Node<T, U> x) {
    assert x != null;
    assert x.parent != null;
    assert x.parent.parent != null;
    assert x == x.parent.left;
    assert x.parent == x.parent.parent.right;

    // Rotate p right and then g left
    //        g                  g              x
    //     d     p    ->      d     x   ->   g     p
    //         x   a              b   p     d b   c a
    //        b c                    c a
    final Node<T, U> p = x.parent;
    final Node<T, U> g = p.parent;
    rotateRight(p);
    rotateLeft(g);
  }

  static <T, U> void zigZigRight(final Node<T, U> x) {
    assert x != null;
    assert x.parent != null;
    assert x.parent.parent != null;
    assert x == x.parent.left;
    assert x.parent == x.parent.parent.left;

    // Rotate g right and then p right
    //        g               p             x
    //     p     d    ->   x     g   ->   a    p
    //   x   c            a b   c d          b   g
    //  a b                                     c d
    final Node<T, U> p = x.parent;
    final Node<T, U> g = p.parent;
    rotateRight(g);
    rotateRight(p);
  }

  static <T, U> void zigZigLeft(final Node<T, U> x) {
    assert x != null;
    assert x.parent != null;
    assert x.parent.parent != null;
    assert x == x.parent.right;
    assert x.parent == x.parent.parent.right;

    // Rotate g left and then p left
    //        g               p                x
    //     d     p    ->   g     x   ->     p     b
    //         c   x      d c   a b       g   a
    //            a b                    d c
    final Node<T, U> p = x.parent;
    final Node<T, U> g = p.parent;
    rotateLeft(g);
    rotateLeft(p);
  }

  static <T, U> void zig(final Node<T, U> x) {
    assert x != null;
    assert x.parent != null;
    assert x.parent.parent == null;

    if (x == x.parent.left) {
      // Rotate right on the edge between p and x
      //     p          x
      //   x   c  ->  a   p
      //  a b            b c
      rotateRight(x.parent);
    } else {
      // Rotate left on the edge between p and x
      //     p          x
      //   a   x  ->  p   c
      //      b c    a b
      rotateLeft(x.parent);
    }
  }

  static <T, U> Node<T, U> rotateRight(final Node<T, U> n) {
    //     n          x
    //   x   c  ->  a   n
    //  a b            b c
    assert n != null;
    final Node<T, U> x = n.left;
    assert x != null;
    final Node<T, U> b = x.right;
    final Node<T, U> p = n.parent;
    if (p != null) {
      if (n == p.left) {
        p.left = x;
      } else {
        p.right = x;
      }
    }
    x.parent = n.parent;
    x.right = n;
    n.parent = x;
    n.left = b;
    if (b != null) {
      b.parent = n;
    }
    return x;
  }

  static <T, U> Node<T, U> rotateLeft(final Node<T, U> n) {
    //     n          x
    //   a   x  ->  n   c
    //      b c    a b
    final Node<T, U> x = n.right;
    final Node<T, U> b = x.left;
    final Node<T, U> p = n.parent;
    if (p != null) {
      if (n == p.left) {
        p.left = x;
      } else {
        p.right = x;
      }
    }
    x.parent = n.parent;
    x.left = n;
    n.parent = x;
    n.right = b;
    if (b != null) {
      b.parent = n;
    }
    return x;
  }

}
