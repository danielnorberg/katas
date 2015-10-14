package ctci.ch18;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_6_SmallestMillion {

  public static void main(String[] args) {
    System.out.println(Arrays.toString(numbers(10)));
    System.out.println(Arrays.toString(smallestBitSet(numbers(1000000), 10)));
//    System.out.println(Arrays.toString(smallestMinHeap(numbers(1000000), 10)));
    assertThat(
        set(rank(new int[]{6, 5, 7, 4, 8, 3, 9, 2, 0, 1}, 5)),
        is(equalTo(set(0, 1, 2, 3, 4))));
  }

  private static Set<Integer> set(final int... values) {
    return IntStream.of(values).mapToObj(Integer::valueOf).collect(Collectors.toSet());
  }

  /**
   * Quickselect.
   *
   * O(n)
   */
  public static int[] rank(final int[] numbers, int rank) {
    return rank(numbers, 0, numbers.length - 1, rank);
  }

  public static int[] rank(final int[] numbers, int left, int right, int rank) {
    final int pivotIx = ThreadLocalRandom.current().nextInt(left, right + 1);
    final int pivot = numbers[pivotIx];
    final int leftEnd = partition(numbers, left, right, pivot);
    final int leftSize = leftEnd - left + 1;
    if (leftSize == rank + 1) {
      final int max = max(numbers, left, leftEnd);
      final int[] smallest = IntStream.of(numbers).filter(x -> x < max).toArray();
      return smallest;
    } else if (rank < leftSize) {
      return rank(numbers, left, leftEnd, rank);
    } else {
      return rank(numbers, leftEnd + 1, right, rank - leftSize);
    }
  }

  private static int rand(final int m) {
    return m / 2;
  }

  private static int max(final int[] numbers, final int start, final int end) {
    if (start >= end) {
      throw new IllegalArgumentException();
    }
    int max = Integer.MIN_VALUE;
    for (int i = start; i < end; i++) {
      final int x = numbers[i];
      if (x > max) {
        max = x;
      }
    }
    return max;
  }

  private static int partition(final int[] a, int left, int right, int pivot) {
    while (true) {
      while (left <= right && a[left] <= pivot) {
        left++;
      }
      while (left <= right && a[right] > pivot) {
        right--;
      }
      if (left > right) {
        return left - 1;
      }
      final int tmp = a[left];
      a[left] = a[right];
      a[right] = tmp;
    }
  }

  private static int[] smallestMinHeap(final int[] numbers, final int i) {
    // ...
    return null;
  }

  private static int[] smallestBitSet(final int[] numbers, final int n) {
    final int[] smallest = new int[n];
    int i = 0;
    final BitSet bs = new BitSet();
    for (final int number : numbers) {
      bs.set(number);
    }
    int j = 0;
    while (i < n) {
      final int number = bs.nextSetBit(j);
      if (number == -1) {
        break;
      }
      j = number + 1;
      smallest[i] = number;
      i++;
    }
    return i == n ? smallest : Arrays.copyOf(smallest, i);
  }

  private static int[] numbers(final int n) {
    return IntStream.generate(Q18_6_SmallestMillion::rand).limit(n).toArray();
  }

  private static int rand() {return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);}
}
