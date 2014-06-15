package median;

import org.junit.Test;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MedianCalculatorTest {

  final MedianCalculator sut = new MedianCalculator();

  @Test
  public void testUnsigned() throws Exception {
    assertThat(sut.median(1, 123, 12345, 123456, 1234567, 12345678), is(123456));
  }

  @Test
  public void testSmallSigned() throws Exception {
    assertThat(sut.median(-3, -2, -1, -1, 0, 1, 2), is(-1));
  }

  @Test
  public void testBigSigned() throws Exception {
    assertThat(sut.median(-1000000000,
                          -100000000,
                          -10000000,
                          -1000000,
                          -100000,
                          -10000,
                          -1000,
                          -100,
                          -10,
                          -1,
                          1,
                          10,
                          100,
                          1000,
                          10000,
                          100000,
                          1000000,
                          10000000,
                          100000000),
               is(-1));
  }

  @Test
  public void testMany() throws Exception {
    final long n = 1L << 30;
    final Supplier<IntStream> values = randStream(n);
    final int m = sut.median(values);
    assertMedian(m, values);
  }

  private Supplier<IntStream> randStream(final long n) {
    final long seed = ThreadLocalRandom.current().nextLong();
    return () -> {
      final SplittableRandom random = new SplittableRandom(seed);
      return IntStream.generate(random::nextInt).limit(n);
    };
  }

  private void assertMedian(final int median, final Supplier<IntStream> values) {
    out.println("median: " + median);
    final Counter eq = new Counter();
    final Counter le = new Counter();
    final Counter ge = new Counter();
    values.get().forEach(v -> {
      if (v == median) {
        eq.inc();
      }
      if (v <= median) {
        le.inc();
      }
      if (v >= median) {
        ge.inc();
      }
    });
    out.println("eq: " + eq);
    out.println("le: " + le);
    out.println("ge: " + ge);
    final long distance = Math.abs(le.value() - ge.value());
    final boolean correct = distance <= eq.value();
    out.println("distance: " + distance);
    assertTrue(median + " is not median!", correct);
  }
}