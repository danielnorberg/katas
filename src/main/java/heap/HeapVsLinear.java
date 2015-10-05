package heap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public class HeapVsLinear {

  @Param({"1", "2", "5", "10", "20", "50", "100"})
  private int n;

  private Integer[] input;

  @Setup
  public void setup() {
    input = ThreadLocalRandom.current().ints(n).mapToObj(Integer::new).toArray(Integer[]::new);
  }

  public static void main(final String... args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + HeapVsLinear.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(2)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public BinaryHeap<Integer> benchmarkHeap() {
    final BinaryHeap<Integer> heap = new BinaryHeap<>(n, Integer::compare);
    for (final int v : input) {
      heap.insert(v);
    }
    return heap;
  }

  @Benchmark
  public SortedLinkedList<Integer> benchmarkSortedLinkedList() {
    final SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);
    for (final int v : input) {
      list.add(v);
    }
    return list;
  }
}
