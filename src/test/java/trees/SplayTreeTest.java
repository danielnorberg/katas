package trees;

import org.junit.Test;

import trees.SplayTree.Node;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static trees.SplayTree.Node.treeEquals;
import static trees.SplayTree.zigZag;

public class SplayTreeTest {

  @Test
  public void testInsert() throws Exception {

    // Insert: 9

    //                      |         Insert            |                ZigZig Left                     |
    //
    //          10                      10                        10                          10
    //      5         15   ->       5         15     ->       5         15      ->        5         15
    //    3   6    13             3   6    13               3   7    13                 3   9    13
    //       4 7                     4 7                       6 9                         7
    //                                  9                     4                           6
    //                                                                                   4

    //                  ZigZag Left-Right
    //
    //          10                          9
    //      9         15      ->       5         10
    //    5        13                3   7          15
    //  3  7                            6          13
    //    6                            4
    //   4

    final Node<Integer, Void> n10 = Node.of(10);
    final Node<Integer, Void> n5 = Node.of(5);
    final Node<Integer, Void> n15 = Node.of(15);
    final Node<Integer, Void> n3 = Node.of(3);
    final Node<Integer, Void> n6 = Node.of(6);
    final Node<Integer, Void> n13 = Node.of(13);
    final Node<Integer, Void> n4 = Node.of(4);
    final Node<Integer, Void> n7 = Node.of(7);
    n10.children(n5, n15);
    n5.children(n3, n6);
    n15.left(n13);
    n6.children(n4, n7);

    final Node<Integer, Void> n9p = Node.of(9);
    final Node<Integer, Void> n5p = Node.of(5);
    final Node<Integer, Void> n10p = Node.of(10);
    final Node<Integer, Void> n7p = Node.of(7);
    final Node<Integer, Void> n3p = Node.of(3);
    final Node<Integer, Void> n15p = Node.of(15);
    final Node<Integer, Void> n6p = Node.of(6);
    final Node<Integer, Void> n13p = Node.of(13);
    final Node<Integer, Void> n4p = Node.of(4);
    n9p.children(n5p, n10p);
    n5p.children(n3p, n7p);
    n10p.right(n15p);
    n7p.left(n6p);
    n6p.left(n4p);
    n15p.left(n13p);

    final SplayTree<Integer, Void> tree = new SplayTree<>(Integer::compare, n10);
    tree.insert(9, null);

    assertThat(treeEquals(tree.root, n9p), is(true));
  }

  @Test
  public void testSplay() throws Exception {
    //          10                              10                                7
    //      5         15          ->        7         15        ->           5         10
    //   3    7    13    17               5   8    13    17                3   6      8     15
    //  1 4  6 8                        3  6                              1 4             13   17
    //                                 1 4

    final Node<Integer, Void> n10 = Node.of(10);
    final Node<Integer, Void> n5 = Node.of(5);
    final Node<Integer, Void> n15 = Node.of(15);
    final Node<Integer, Void> n3 = Node.of(3);
    final Node<Integer, Void> n7 = Node.of(7);
    final Node<Integer, Void> n13 = Node.of(13);
    final Node<Integer, Void> n17 = Node.of(17);
    final Node<Integer, Void> n1 = Node.of(1);
    final Node<Integer, Void> n4 = Node.of(4);
    final Node<Integer, Void> n6 = Node.of(6);
    final Node<Integer, Void> n8 = Node.of(8);
    n10.children(n5, n15);
    n5.children(n3, n7);
    n15.children(n13, n17);
    n3.children(n1, n4);
    n7.children(n6, n8);

    SplayTree.splay(n7);

    final Node<Integer, Void> n7p = Node.of(7);
    final Node<Integer, Void> n5p = Node.of(5);
    final Node<Integer, Void> n10p = Node.of(10);
    final Node<Integer, Void> n3p = Node.of(3);
    final Node<Integer, Void> n6p = Node.of(6);
    final Node<Integer, Void> n8p = Node.of(8);
    final Node<Integer, Void> n15p = Node.of(15);
    final Node<Integer, Void> n1p = Node.of(1);
    final Node<Integer, Void> n4p = Node.of(4);
    final Node<Integer, Void> n13p = Node.of(13);
    final Node<Integer, Void> n17p = Node.of(17);
    n7p.children(n5p, n10p);
    n5p.children(n3p, n6p);
    n10p.children(n8p, n15p);
    n3p.children(n1p, n4p);
    n15p.children(n13p, n17p);

    assertThat(treeEquals(n7, n7p), is(true));
  }

  @Test
  public void testFind() throws Exception {
    //          10                              10                                7
    //      5         15          ->        7         15        ->           5         10
    //   3    7    13    17               5   8    13    17                3   6      8     15
    //  1 4  6 8                        3  6                              1 4             13   17
    //                                 1 4

    final Node<Integer, Void> n10 = Node.of(10);
    final Node<Integer, Void> n5 = Node.of(5);
    final Node<Integer, Void> n15 = Node.of(15);
    final Node<Integer, Void> n3 = Node.of(3);
    final Node<Integer, Void> n7 = Node.of(7);
    final Node<Integer, Void> n13 = Node.of(13);
    final Node<Integer, Void> n17 = Node.of(17);
    final Node<Integer, Void> n1 = Node.of(1);
    final Node<Integer, Void> n4 = Node.of(4);
    final Node<Integer, Void> n6 = Node.of(6);
    final Node<Integer, Void> n8 = Node.of(8);
    n10.children(n5, n15);
    n5.children(n3, n7);
    n15.children(n13, n17);
    n3.children(n1, n4);
    n7.children(n6, n8);

    final SplayTree<Integer, Void> tree = new SplayTree<>(Integer::compare, n10);

    final Node<Integer, Void> n = tree.find(7);
    assertThat(n, is(n7));

    final Node<Integer, Void> n7p = Node.of(7);
    final Node<Integer, Void> n5p = Node.of(5);
    final Node<Integer, Void> n10p = Node.of(10);
    final Node<Integer, Void> n3p = Node.of(3);
    final Node<Integer, Void> n6p = Node.of(6);
    final Node<Integer, Void> n8p = Node.of(8);
    final Node<Integer, Void> n15p = Node.of(15);
    final Node<Integer, Void> n1p = Node.of(1);
    final Node<Integer, Void> n4p = Node.of(4);
    final Node<Integer, Void> n13p = Node.of(13);
    final Node<Integer, Void> n17p = Node.of(17);
    n7p.children(n5p, n10p);
    n5p.children(n3p, n6p);
    n10p.children(n8p, n15p);
    n3p.children(n1p, n4p);
    n15p.children(n13p, n17p);

    assertThat(treeEquals(n7, n7p), is(true));
  }

  @Test
  public void testZigZagLeftRight() throws Exception {
    // Rotate p left and then g right
    //        g                  g              x
    //     p     d    ->      x     d   ->   p     g
    //   a   x              p  c            a b   c d
    //      b c            a b
    final Node<Character, Void> g1 = Node.of('g');
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> d1 = Node.of('d');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> b1 = Node.of('b');
    final Node<Character, Void> c1 = Node.of('c');
    g1.children(p1, d1);
    p1.children(a1, x1);
    x1.children(b1, c1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> g2 = Node.of('g');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> c2 = Node.of('c');
    final Node<Character, Void> d2 = Node.of('d');
    x2.children(p2, g2);
    p2.children(a2, b2);
    g2.children(c2, d2);

    zigZag(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testZigZagRightLeft() throws Exception {
    // Rotate p right and then g left
    //        g                  g              x
    //     d     p    ->      d     x   ->   g     p
    //         x   a              b   p     d b   c a
    //        b c                    c a
    final Node<Character, Void> g1 = Node.of('g');
    final Node<Character, Void> d1 = Node.of('d');
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> b1 = Node.of('b');
    final Node<Character, Void> c1 = Node.of('c');
    g1.children(d1, p1);
    p1.children(x1, a1);
    x1.children(b1, c1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> g2 = Node.of('g');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> d2 = Node.of('d');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> c2 = Node.of('c');
    final Node<Character, Void> a2 = Node.of('a');
    x2.children(g2, p2);
    g2.children(d2, b2);
    p2.children(c2, a2);

    zigZag(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testZigZigRight() throws Exception {
    // Rotate g right and then p right
    //        g               p             x
    //     p     d    ->   x     g   ->   a    p
    //   x   c            a b   c d          b   g
    //  a b                                     c d
    final Node<Character, Void> g1 = Node.of('g');
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> d1 = Node.of('d');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> c1 = Node.of('c');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> b1 = Node.of('b');
    g1.children(p1, d1);
    p1.children(x1, c1);
    x1.children(a1, b1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> g2 = Node.of('g');
    final Node<Character, Void> c2 = Node.of('c');
    final Node<Character, Void> d2 = Node.of('d');
    x2.children(a2, p2);
    p2.children(b2, g2);
    g2.children(c2, d2);

    zigZag(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testZigZigLeft() throws Exception {
    // Rotate g left and then p left
    //        g               p                x
    //     d     p    ->   g     x   ->     p     b
    //         c   x      d c   a b       g   a
    //            a b                    d c
    final Node<Character, Void> g1 = Node.of('g');
    final Node<Character, Void> d1 = Node.of('d');
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> c1 = Node.of('c');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> b1 = Node.of('b');
    g1.children(d1, p1);
    p1.children(c1, x1);
    x1.children(a1, b1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> g2 = Node.of('g');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> d2 = Node.of('d');
    final Node<Character, Void> c2 = Node.of('c');
    x2.children(p2, b2);
    p2.children(g2, a2);
    g2.children(d2, c2);

    zigZag(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testZigRight() throws Exception {
    //     p          x
    //   x   c  ->  a   p
    //  a b            b c
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> c1 = Node.of('c');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> b1 = Node.of('b');
    p1.children(x1, c1);
    x1.children(a1, b1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> c2 = Node.of('c');
    x2.children(a2, p2);
    p2.children(b2, c2);

    SplayTree.zig(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testZigLeft() throws Exception {
    //     p          x
    //   a   x  ->  p   c
    //      b c    a b
    final Node<Character, Void> p1 = Node.of('p');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> b1 = Node.of('b');
    final Node<Character, Void> c1 = Node.of('c');
    p1.children(a1, x1);
    x1.children(b1, c1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> p2 = Node.of('p');
    final Node<Character, Void> c2 = Node.of('c');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> b2 = Node.of('b');
    x2.children(p2, c2);
    p2.children(a2, b2);

    SplayTree.zig(x1);

    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testRotateRight() throws Exception {
    //     n          x
    //   x   c  ->  a   n
    //  a b            b c
    final Node<Character, Void> n1 = Node.of('n');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> c1 = Node.of('c');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> b1 = Node.of('b');
    n1.children(x1, c1);
    x1.children(a1, b1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> n2 = Node.of('n');
    final Node<Character, Void> c2 = Node.of('c');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> b2 = Node.of('b');
    x2.children(a2, n2);
    n2.children(b2, c2);

    final Node<Character, Void> x1r = SplayTree.rotateRight(n1);
    assertThat(x1r, is(x1));
    assertThat(treeEquals(x1, x2), is(true));
  }

  @Test
  public void testRotateLeft() throws Exception {
    //     n          x
    //   a   x  ->  n   c
    //      b c    a b
    final Node<Character, Void> n1 = Node.of('n');
    final Node<Character, Void> a1 = Node.of('a');
    final Node<Character, Void> x1 = Node.of('x');
    final Node<Character, Void> b1 = Node.of('b');
    final Node<Character, Void> c1 = Node.of('c');
    n1.children(a1, x1);
    x1.children(b1, c1);

    final Node<Character, Void> x2 = Node.of('x');
    final Node<Character, Void> n2 = Node.of('n');
    final Node<Character, Void> a2 = Node.of('a');
    final Node<Character, Void> b2 = Node.of('b');
    final Node<Character, Void> c2 = Node.of('c');
    x2.children(n2, c2);
    n2.children(a2, b2);

    final Node<Character, Void> x1r = SplayTree.rotateLeft(n1);
    assertThat(x1r, is(x1));
    assertThat(treeEquals(x1, x2), is(true));
  }
}