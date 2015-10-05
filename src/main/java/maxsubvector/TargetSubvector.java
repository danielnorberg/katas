package maxsubvector;

import com.google.common.collect.Range;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@State(Scope.Benchmark)
public class TargetSubvector {

  @Param({"100", "1000", "10000", "100000"})
  private int n;

  private double[] input;

  @Setup
  public void setup() {
    input = ThreadLocalRandom.current().doubles(n).toArray();
  }

  public static void main(final String... args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + TargetSubvector.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(2)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public Range<Integer> benchmark() {
    return targetSubvector(ThreadLocalRandom.current().nextDouble(), input);
  }

  public static Range<Integer> targetSubvector(final double target, final double... input) {
    if (input.length == 0) {
      return Range.closedOpen(-1, -1);
    }

    Prefix[] prefixes = new Prefix[input.length + 1];
    prefixes[0] = new Prefix(0, 0);
    double sum = 0;
    for (int i = 0; i < input.length; i++) {
      sum += input[i];
      prefixes[i + 1] = new Prefix(i + 1, sum);
    }
    Arrays.sort(prefixes);

    double minErr = Double.MAX_VALUE;
    int start = 0;
    int end = 1;

    for (int i = 1; i < input.length + 1; i++) {
      final double diff = prefixes[i].sum - prefixes[i - 1].sum;
      final double err = Math.abs(target - diff);
      if (err < minErr) {
        minErr = err;
        start = min(prefixes[i - 1].end, prefixes[i].end);
        end = max(prefixes[i - 1].end, prefixes[i].end);
      }
    }

    return Range.closedOpen(start, end);
  }

  private static class Prefix implements Comparable<Prefix> {

    private final int end;
    private final double sum;

    public Prefix(final int end, final double sum) {
      this.end = end;
      this.sum = sum;
    }

    @Override
    public int compareTo(final Prefix o) {
      return Double.compare(sum, o.sum);
    }

    @Override
    public String toString() {
      return "Prefix{" +
             "end=" + end +
             ", sum=" + sum +
             '}';
    }
  }
}
