package mergesort;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.google.common.primitives.Ints.asList;
import static java.util.Collections.singletonList;
import static mergesort.MergeSort.mergeSort;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MergeSortTest {

  @Test
  public void testEmpty() throws Exception {
    assertThat(mergeSort(Collections.<Integer>emptyList()), is(Collections.<Integer>emptyList()));
  }

  @Test
  public void testSingleton() throws Exception {
    assertThat(mergeSort(singletonList(1)), is(singletonList(1)));
  }

  @Test
  public void testTwoElements() throws Exception {
    assertThat(mergeSort(asList(1, 2)), is(asList(1, 2)));
    assertThat(mergeSort(asList(2, 1)), is(asList(1, 2)));
  }

  @Test
  public void testThreeElements() throws Exception {
    assertThat(mergeSort(asList(1, 2, 3)), is(asList(1, 2, 3)));
    assertThat(mergeSort(asList(1, 3, 2)), is(asList(1, 2, 3)));
    assertThat(mergeSort(asList(2, 1, 3)), is(asList(1, 2, 3)));
    assertThat(mergeSort(asList(2, 3, 1)), is(asList(1, 2, 3)));
    assertThat(mergeSort(asList(3, 1, 2)), is(asList(1, 2, 3)));
    assertThat(mergeSort(asList(3, 2, 1)), is(asList(1, 2, 3)));
  }

  @Test
  public void testLargeList() throws Exception {
    final List<Integer> input = asList(new Random(4711).ints(10000).toArray());
    final List<Integer> output = mergeSort(input);
    final List<Integer> expected = input.stream().sorted().collect(Collectors.toList());
    assertThat(output, is(expected));
  }
}