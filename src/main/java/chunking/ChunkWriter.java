package chunking;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.AbstractSpliterator;

public class ChunkWriter {

  private final ByteBuffer header = ByteBuffer.allocate(4);
  private final int size;

  private ByteBuffer chunk;

  public ChunkWriter(final int size) {
    this.size = size;
    this.chunk = chunk();
  }

  public Stream<ByteBuffer> inject(final ByteBuffer message) {
    if (message == null) {
      if (chunk.position() > 0) {
        chunk.flip();
        return Stream.of(chunk, null);
      } else {
        return Stream.of((ByteBuffer) null);
      }
    }
    final long est = message.remaining() / size;
    return StreamSupport.stream(new AbstractSpliterator<ByteBuffer>(est, ORDERED) {

      @Override
      public void forEachRemaining(final Consumer<? super ByteBuffer> action) {
        header.putInt(message.remaining());
        header.flip();
        inject0(header, action);
        header.flip();
        inject0(message, action);
      }

      @Override
      public boolean tryAdvance(final Consumer<? super ByteBuffer> action) {
        throw new UnsupportedOperationException();
      }
    }, false);
  }

  private void inject0(final ByteBuffer buffer, final Consumer<? super ByteBuffer> action) {
    while (buffer.hasRemaining()) {
      final int n = Math.min(chunk.remaining(), buffer.remaining());
      final ByteBuffer slice = buffer.slice();
      slice.limit(n);
      buffer.position(buffer.position() + n);
      chunk.put(slice);
      if (chunk.remaining() == 0) {
        chunk.flip();
        action.accept(chunk);
        chunk = chunk();
      }
    }
  }

  private ByteBuffer chunk() {
    return ByteBuffer.allocate(size);
  }
}
