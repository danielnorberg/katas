package heap;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BinaryHeapTest {

  @Test
  public void testInsertExtract() throws Exception {
    final BinaryHeap<Integer> heap = new BinaryHeap<>(Integer::compare);
    assertThat(heap.size(), is(0));
    heap.insert(4);
    assertThat(heap.size(), is(1));
    heap.insert(3);
    assertThat(heap.size(), is(2));
    heap.insert(2);
    assertThat(heap.size(), is(3));
    heap.insert(1);
    assertThat(heap.size(), is(4));
    assertThat(heap.extract(), is(1));
    assertThat(heap.size(), is(3));
    assertThat(heap.extract(), is(2));
    assertThat(heap.size(), is(2));
    assertThat(heap.extract(), is(3));
    assertThat(heap.size(), is(1));
    assertThat(heap.extract(), is(4));
    assertThat(heap.size(), is(0));
  }

  @Test
  public void testInsert() throws Exception {
    final int n = 64;
    final int[] values = ThreadLocalRandom.current().ints().limit(n).toArray();
    final BinaryHeap<Integer> heap = new BinaryHeap<>(n, Integer::compare);
    for (final int value : values) {
      heap.insert(value);
    }
    System.out.println(heap);
    final int[] extracted = IntStream.range(0, n).map(i -> heap.extract()).toArray();
    final int[] sorted = IntStream.of(values).sorted().toArray();
    assertThat(extracted, is(equalTo(sorted)));
    System.out.println(heap);
  }
}