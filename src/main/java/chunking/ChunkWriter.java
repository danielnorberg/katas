package chunking;

import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Stream;

public class ChunkWriter {

  private final ByteBuffer header = ByteBuffer.allocate(4);
  private final int size;

  private List<ByteBuffer> chunks = Lists.newArrayList();
  private ByteBuffer chunk;

  public ChunkWriter(final int size) {
    this.size = size;
    this.chunk = chunk();
  }

  public Stream<ByteBuffer> inject(final ByteBuffer message) {
    if (message != null) {
      header.putInt(message.remaining());
      header.flip();
      inject0(header);
      header.flip();
      inject0(message);
    }
    if (message == null) {
      if (chunk.position() > 0) {
        flush();
      }
      chunks.add(null);
    }
    if (chunks.size() == 1) {
      final Stream<ByteBuffer> s = Stream.of(chunks.get(0));
      chunks.clear();
      return s;
    } else {
      final List<ByteBuffer> completed = chunks;
      chunks = Lists.newArrayList();
      return completed.stream();
    }
  }

  private void flush() {
    chunk.flip();
    chunks.add(chunk);
    chunk = chunk();
  }

  private void inject0(final ByteBuffer buffer) {
    while (buffer.hasRemaining()) {
      final int n = Math.min(chunk.remaining(), buffer.remaining());
      final ByteBuffer slice = buffer.slice();
      slice.limit(n);
      buffer.position(buffer.position() + n);
      chunk.put(slice);
      if (chunk.remaining() == 0) {
        flush();
      }
    }
  }

  private ByteBuffer chunk() {
    return ByteBuffer.allocate(size);
  }
}
