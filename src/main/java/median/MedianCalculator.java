package median;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * A utility for calculating the median of large streams of 32 bit signed integers. The integer
 * stream need not fit in ram or on disk.
 *
 * The median is calculated by performing two passes; first the median of the 16 highest bits is
 * calculated and secondly the median within that set is calculated. The final median is then
 * the combination of the two.
 *
 * http://stackoverflow.com/questions/3572640/interview-question-find-median-from-mega-number-of-integers/3576479#3576479
 */
public class MedianCalculator {

  private final boolean debug;

  public MedianCalculator(final boolean debug) {
    this.debug = debug;
  }

  public MedianCalculator() {
    this(false);
  }

  public int median(final int... values) {
    return median(() -> IntStream.of(values));
  }

  public int median(final Supplier<IntStream> values) {
    printValues(values.get());

    // hi
    final long[] h = new long[65536];
    final long count = values.get().reduce(0, (c, v) -> {
      h[v >>> 16]++;
      return c + 1;
    });
    final long mid = count / 2;
    printHistogram("hi", h);
    long n = 0;
    int hix = 0x8000;
    for (int i = -0x8000; i < 0x8000; i++) {
      hix = i & 0xFFFF;
      n += h[hix];
      if (n > mid) {
        break;
      }
    }
    n -= h[hix];
    final int hi = hix << 16;
    if (debug) {
      System.out.println();
      System.out.printf("hi: %10d (%04x)%n", hi, hi);
    }

    // lo
    Arrays.fill(h, 0);
    values.get()
        .filter(v -> (v & 0xFFFF0000) == hi)
        .forEach(v -> h[v & 0x0000FFFF]++);
    printHistogram("lo", h);
    int lix;
    for (lix = 0; lix < 0x10000; lix++) {
      n += h[lix];
      if (n > mid) {
        break;
      }
    }
    final int lo = lix;

    if (debug) {
      System.out.println();
      System.out.printf("lo: %10d (%04x)%n", lo, lo);
    }

    final int median = hi + lo;
    if (debug) {
      System.out.println();
      System.out.printf("median: %10d (%04x)%n", median, median);
    }
    return median;
  }

  private void printValues(final IntStream values) {
    if (!debug) {
      return;
    }
    System.out.println();
    System.out.println("values");
    System.out.println("-------------------------------");
    System.out.println("        ix          v   hi   lo");
    values.asLongStream().reduce(0, (i, v) -> {
      final int hi = (int) v >>> 16;
      final int lo = (int) v & 0xFFFF;
      System.out.printf("%10d %10d %04x %04x%n", i, (int) v, hi, lo);
      return i + 1;
    });
  }


  private void printHistogram(final String type, final long[] h) {
    if (!debug) {
      return;
    }
    System.out.println();
    System.out.println(type + " histogram");
    System.out.println("---------------");
    for (int i = 0; i < h.length; i++) {
      if (h[i] != 0) {
        System.out.printf("%d (%04x): %d%n", i, i, h[i]);
      }
    }
  }
}
