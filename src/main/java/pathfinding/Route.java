package pathfinding;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static pathfinding.Node.distance2;

class Route {

  final List<Node> route;
  final float length;

  Route(final List<Node> route, final float length) {
    this.route = route;
    this.length = length;
  }

  @Override
  public String toString() {
    return "Route{" +
           "route=" + route.stream().map(n -> n.name + "(" + n.x + "," + n.y + ")").collect(joining(", ")) +
           ", length=" + length +
           '}';
  }

  Route with(final Node node, final int ix) {
    final ImmutableList.Builder<Node> b = ImmutableList.builder();
    float length = 0;
    Node prev = route.get(route.size() - 1);
    Node next;
    for (int i = 0; i < route.size(); i++) {
      if (i == ix) {
        b.add(node);
        length += distance2(prev, node);
        prev = node;
      }
      next = route.get(i);
      b.add(next);
      length += distance2(prev, next);
      prev = next;
    }
    return new Route(b.build(), length);
  }
}
