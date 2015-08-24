package trees;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AVLTreeTest {

  @Test
  public void testInsert() throws Exception {
    final AVLTree<Integer> tree = new AVLTree<>(Integer::compare);

    // Insert 10
    // ---------
    // 10
    tree.insert(10);
    assertThat(tree.root.value, is(10));
    assertThat(tree.root.balance, is(0));
    assertThat(tree.root.height, is(0));
    assertThat(tree.root.left, is(nullValue()));
    assertThat(tree.root.right, is(nullValue()));

    // Insert 5
    // --------
    //  10
    // 5
    tree.insert(5);
    assertThat(tree.root.value, is(10));
    assertThat(tree.root.balance, is(1));
    assertThat(tree.root.height, is(1));
    assertThat(tree.root.left.value, is(5));
    assertThat(tree.root.left.balance, is(0));
    assertThat(tree.root.left.height, is(0));
    assertThat(tree.root.left.left, is(nullValue()));
    assertThat(tree.root.left.right, is(nullValue()));


    // Insert 1 - Rebalance
    // --------------------
    //   10        5
    //  5      => 1 10
    // 1
    tree.insert(1);
    assertThat(tree.root.value, is(5));
    assertThat(tree.root.balance, is(0));
    assertThat(tree.root.height, is(1));
    assertThat(tree.root.left.value, is(1));
    assertThat(tree.root.left.balance, is(0));
    assertThat(tree.root.left.height, is(0));
    assertThat(tree.root.left.left, is(nullValue()));
    assertThat(tree.root.left.right, is(nullValue()));
    assertThat(tree.root.right.value, is(10));
    assertThat(tree.root.right.balance, is(0));
    assertThat(tree.root.right.height, is(0));
    assertThat(tree.root.right.right, is(nullValue()));
    assertThat(tree.root.right.left, is(nullValue()));

  }
}