package chunking;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class ChunkingTest {

  @Test
  public void test() {
    final Supplier<IntStream> sizes = sizes(1_000_000_000);
    final Supplier<Stream<ByteBuffer>> input = messages(sizes);
    final Stream<ByteBuffer> chunks = Chunking.write(input.get(), 8);
    final Stream<ByteBuffer> output = Chunking.read(chunks);
    zip(input.get(), output, Pair::of).forEach(p -> assertEquals(p.a, p.b));
  }

  private <T, U, R> Stream<R> zip(final Stream<T> a, final Stream<U> b,
                                  final BiFunction<T, U, R> zipper) {
    return zip(a.spliterator(), b.spliterator(), zipper);
  }

  private <T, U, R> Stream<R> zip(final Spliterator<T> a, final Spliterator<U> b,
                                  final BiFunction<T, U, R> zipper) {
    final long est = Math.min(a.estimateSize(), b.estimateSize());
    final Merger<T, U> merger = new Merger<>();
    return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(est, 0) {
      @Override
      public boolean tryAdvance(final Consumer<? super R> action) {
        final boolean ar = a.tryAdvance(merger.t());
        final boolean br = b.tryAdvance(merger.u());
        if (!ar || !br) {
          return false;
        } else {
          merger.merge(zipper);
          return true;
        }
      }
    }, false);
  }

  private Supplier<IntStream> sizes(final long n) {
    final long seed = ThreadLocalRandom.current().nextLong();
    return () -> {
      final SplittableRandom random = new SplittableRandom(seed);
      return IntStream.concat(random.ints(n, 1, 1024), IntStream.of(0));
    };
  }

  private Supplier<Stream<ByteBuffer>> messages(final Supplier<IntStream> sizes) {
    final long seed = ThreadLocalRandom.current().nextLong();
    return () -> {
      final SplittableRandom random = new SplittableRandom(seed);
      return sizes.get().mapToObj((size) -> message(size, random));
    };
  }

  private static ByteBuffer message(final int size, final SplittableRandom random) {
    if (size == 0) {
      return null;
    }
    final byte[] bytes = new byte[size];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (random.nextInt() & 0xFF);
    }
    return ByteBuffer.wrap(bytes);
  }

  private static class Merger<T, U> {

    T t;
    U u;

    public Consumer<T> t() {
      return nt -> t = nt;
    }

    public Consumer<U> u() {
      return nu -> u = nu;
    }

    public void merge(BiFunction<T, U, ?> destination) {
      destination.apply(t, u);
    }
  }

  private static class Pair<A, B> {

    private final A a;
    private final B b;

    public Pair(final A a, final B b) {
      this.a = a;
      this.b = b;
    }

    public static <A, B> Pair<A, B> of(final A a, final B b) {
      return new Pair<>(a, b);
    }
  }
}
