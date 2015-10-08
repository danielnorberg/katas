package quicksort;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.google.common.primitives.Ints.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static quicksort.GenericQuickSort.quickSorted;

public class GenericQuickSortTest {

  @Test
  public void testEmpty() throws Exception {
    assertThat(quickSorted(Collections.<Integer>emptyList()), is(Collections.<Integer>emptyList()));
  }

  @Test
  public void testSingleton() throws Exception {
    assertThat(quickSorted(singletonList(1)), is(singletonList(1)));
  }

  @Test
  public void testTwoElements() throws Exception {
    assertThat(quickSorted(asList(1, 2)), is(asList(1, 2)));
    assertThat(quickSorted(asList(2, 1)), is(asList(1, 2)));
  }

  @Test
  public void testThreeElements() throws Exception {
    assertThat(quickSorted(asList(1, 2, 3)), is(asList(1, 2, 3)));
    assertThat(quickSorted(asList(1, 3, 2)), is(asList(1, 2, 3)));
    assertThat(quickSorted(asList(2, 1, 3)), is(asList(1, 2, 3)));
    assertThat(quickSorted(asList(2, 3, 1)), is(asList(1, 2, 3)));
    assertThat(quickSorted(asList(3, 1, 2)), is(asList(1, 2, 3)));
    assertThat(quickSorted(asList(3, 2, 1)), is(asList(1, 2, 3)));
  }

  @Test
  public void testLargeList() throws Exception {
    final List<Integer> input = asList(new Random(4711).ints(10000).toArray());
    final List<Integer> output = quickSorted(input);
    final List<Integer> expected = input.stream().sorted().collect(Collectors.toList());
    assertThat(output, is(expected));
  }

}