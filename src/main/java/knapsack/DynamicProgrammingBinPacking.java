package knapsack;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static knapsack.DynamicProgrammingBinPacking.Item.item;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DynamicProgrammingBinPacking {

  public static class Item {

    private final int value;
    private final int weight;

    public Item(final int value, final int weight) {
      this.value = value;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return "Item{" +
             "value=" + value +
             ", weight=" + weight +
             '}';
    }

    public static Item item(final int value, final int weight) {
      return new Item(value, weight);
    }
  }

  public static void main(String[] args) {
    final Item a = item(4, 12);
    final Item b = item(2, 2);
    final Item c = item(2, 1);
    final Item d = item(1, 1);
    final Item e = item(10, 4);

    final List<Item> items = ImmutableList.of(a, b, c, d, e);
    final int W = 15;

    final List<Item> selection = pack(W, items);
    assertThat(selection, is(asList(b, c, d, e)));
  }

  private static List<Item> pack(final int W, final List<Item> items) {
    final Boolean[][] m = new Boolean[W + 1][];
    pack(m, W, items, 0);
    int w = W;
    final List<Item> selection = new ArrayList<>();
    for (int i = 0; i < items.size(); i++) {
      final boolean include = m[w][i];
      if (include) {
        final Item item = items.get(i);
        selection.add(item);
        w -= item.weight;
      }
    }
    return selection;
  }

  private static int pack(final Boolean[][] m, final int w, final List<Item> items, final int i) {
    if (i == items.size()) {
      return 0;
    }
    if (m[w] == null) {
      m[w] = new Boolean[items.size()];
    }
    if (m[w][i] != null) {
      return value(m, w, items, i);
    }
    final Item item = items.get(i);
    if (item.weight > w) {
      m[w][i] = false;
      return pack(m, w, items, i + 1);
    }
    final int valueWithItem = item.value + pack(m, w - item.weight, items, i + 1);
    final int valueWithoutItem = pack(m, w, items, i + 1);
    if (valueWithItem > valueWithoutItem) {
      m[w][i] = true;
      return valueWithItem;
    } else {
      m[w][i] = false;
      return valueWithoutItem;
    }
  }

  private static int value(final Boolean[][] m, final int w, final List<Item> items, final int i) {
    if (i == items.size()) {
      return 0;
    }
    if (m[w][i]) {
      final Item item = items.get(i);
      return item.value + value(m, w - item.weight, items, i + 1);
    } else {
      return value(m, w, items, i + 1);
    }
  }
}
