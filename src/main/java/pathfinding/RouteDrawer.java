package pathfinding;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.google.common.collect.Iterables.getLast;

public class RouteDrawer extends Application {

  private static List<Node> route;

  public static void draw(Route route) {
    draw(route.route);
  }

  public static void draw(final List<Node> route) {
    RouteDrawer.route = route;
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Route");
    Group root = new Group();
    Canvas canvas = new Canvas(800, 800);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    drawRoute(gc);
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  private void drawRoute(GraphicsContext gc) {
//    float minX = Float.MAX_VALUE;
//    float minY = Float.MAX_VALUE;
//    float maxX = Float.MIN_VALUE;
//    float maxY = Float.MIN_VALUE;
//
//    for (final Node node : route.route) {
//      minX = min(minX, node.x);
//      minY = min(minY, node.y);
//      maxX = max(maxX, node.x);
//      maxY = max(maxY, node.y);
//    }
//
//    float w = maxX - minX;
//    float h = maxY - minY;

    gc.setFill(Color.GREEN);
    gc.setStroke(Color.BLUE);

    Node prev = getLast(route);
    for (final Node node : route) {
      gc.fillOval(node.x - 2, node.y - 2, 4, 4);
      gc.strokeLine(prev.x, prev.y, node.x, node.y);
      prev = node;
    }
  }
}
