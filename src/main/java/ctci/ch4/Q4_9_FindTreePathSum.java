package ctci.ch4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class Q4_9_FindTreePathSum {

  private static class Node {

    private final String name;
    private final int value;
    private Node left;
    private Node right;

    public Node(final String name, final int value, final Node left, final Node right) {
      this.name = name;
      this.value = value;
      this.left = left;
      this.right = right;
    }

    public Node(final String name, final int value) {
      this(name, value, null, null);
    }

    @Override
    public String toString() {
      return name + "=" + value;
    }

    public int value() {
      return value;
    }
  }

  public static void main(final String... args) {
    //         a=1
    //        /   \
    //     b=2     c=1
    //     / \     /  \
    //   d=3 e=2  f=4  g=3
    //            /
    //           h=0

    final Node h = new Node("h", 0);
    final Node f = new Node("f", 4, h, null);
    final Node g = new Node("g", 3);
    final Node d = new Node("d", 3);
    final Node e = new Node("e", 2);
    final Node b = new Node("b", 2, d, e);
    final Node c = new Node("c", 1, f, g);
    final Node a = new Node("a", 1, b, c);

    //noinspection unchecked
    assertThat(findPaths(5, a), containsInAnyOrder(
        asList(b, d),
        asList(a, b, e),
        asList(a, c, g),
        asList(c, f),
        asList(c, f, h)));

  }

  private static class Path {

    private int sum = 0;
    private List<Node> nodes = new ArrayList<>();

    public Path(final Node node) {
      push(node);
    }

    private void push(Node node) {
      sum += node.value;
      nodes.add(node);
    }

    private void pop() {
      final Node node = nodes.remove(nodes.size() - 1);
      sum -= node.value;
    }
  }

  private static Collection<List<Node>> findPaths(final int sum, final Node root) {
    final List<List<Node>> result = new ArrayList<>();
    final List<Path> paths = new ArrayList<>();
    findPaths(result, paths, 0, sum, root);
    return result;
  }

  private static void findPaths(final List<List<Node>> result, final List<Path> paths,
                                final int level, final int needle, final Node node) {
    for (int i = 0; i < paths.size(); i++) {
      paths.get(i).push(node);
    }
    paths.add(new Path(node));

    for (int i = 0; i < paths.size(); i++) {
      final Path path = paths.get(i);
      if (path.sum == needle) {
        result.add(new ArrayList<>(path.nodes));
      }
    }

    if (node.left != null) {
      findPaths(result, paths, level + 1, needle, node.left);
    }
    if (node.right != null) {
      findPaths(result, paths, level + 1, needle, node.right);
    }

    paths.remove(paths.size() - 1);
    for (int i = 0; i < paths.size(); i++) {
      paths.get(i).pop();
    }
  }


}
