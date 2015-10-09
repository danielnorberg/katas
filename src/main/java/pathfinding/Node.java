package pathfinding;

class Node {

  final String name;
  final float x;
  final float y;

  Node(final String name, final int x, final int y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "Node{" +
           "name='" + name + '\'' +
           ", x=" + x +
           ", y=" + y +
           '}';
  }

  /**
   * |a-b|^2
   */
  static float distance2(final Node a, final Node b) {
    final float dx = b.x - a.x;
    final float dy = b.y - a.y;
    return dx * dx + dy * dy;
  }
}
