package chunking;

import java.nio.ByteBuffer;
import java.util.stream.Stream;

/**
 * An unnecessarily complicated way to do message chunking using Streams.
 */
public class Chunking {

  public static Stream<ByteBuffer> write(final Stream<ByteBuffer> messages, final int size) {
    final ChunkWriter writer = new ChunkWriter(size);
    return messages.flatMap(writer::inject);
  }

  public static Stream<ByteBuffer> read(final Stream<ByteBuffer> chunks) {
    final ChunkReader reader = new ChunkReader();
    return chunks.flatMap(reader::inject);
  }
}
