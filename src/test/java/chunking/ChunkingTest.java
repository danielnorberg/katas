package chunking;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChunkingTest {

  @Test
  public void test() {
    final Supplier<IntStream> sizes = sizes(100_000);
    final Supplier<Stream<ByteBuffer>> input = messages(sizes);
    final Stream<ByteBuffer> chunks = Chunking.write(input.get(), 8);
    final Stream<ByteBuffer> output = Chunking.read(chunks);
    final List<ByteBuffer> inputList = input.get().collect(toList());
    final List<ByteBuffer> outputList = output.collect(toList());
    assertThat(outputList, is(inputList));
  }

  private Supplier<IntStream> sizes(final long n) {
    final long seed = ThreadLocalRandom.current().nextLong();
    return () -> {
      final SplittableRandom random = new SplittableRandom(seed);
      return IntStream.concat(random.ints(n, 1, 128), IntStream.of(0));
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
}
