package chunking;

import java.nio.ByteBuffer;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

public class ChunkReader {

  private enum State {
    HEADER,
    PAYLOAD
  }

  private final ByteBuffer header = ByteBuffer.allocate(4);

  private State state = State.HEADER;
  private ByteBuffer message;

  public Stream<ByteBuffer> inject(final ByteBuffer chunk) {
    if (chunk == null) {
      return Stream.of((ByteBuffer) null);
    }
    final long est = chunk.remaining() / 8;
    return StreamSupport.stream(new Spliterators.AbstractSpliterator<ByteBuffer>(est, ORDERED) {
      @Override
      public void forEachRemaining(final Consumer<? super ByteBuffer> action) {
        while (chunk.hasRemaining()) {
          switch (state) {
            case HEADER:
              header(chunk);
              break;
            case PAYLOAD:
              payload(chunk, action);
              break;
          }
        }
      }

      @Override
      public boolean tryAdvance(final Consumer<? super ByteBuffer> action) {
        throw new UnsupportedOperationException();
      }
    }, false);
  }

  private void payload(final ByteBuffer chunk, final Consumer<? super ByteBuffer> action) {
    final int n = Math.min(message.remaining(), chunk.remaining());
    message.put(readSlice(n, chunk));
    if (!message.hasRemaining()) {
      final ByteBuffer completed = message;
      message = null;
      state = State.HEADER;
      completed.flip();
      action.accept(completed);
    }
  }

  private ByteBuffer readSlice(final int n, final ByteBuffer buffer) {
    final ByteBuffer slice = buffer.slice();
    slice.limit(n);
    buffer.position(buffer.position() + n);
    return slice;
  }

  private void header(final ByteBuffer chunk) {
    if (header.hasRemaining()) {
      final int n = Math.min(header.remaining(), chunk.remaining());
      header.put(readSlice(n, chunk));
    }
    if (header.hasRemaining()) {
      return;
    }
    header.flip();
    final int size = header.getInt();
    message = ByteBuffer.allocate(size);
    header.flip();
    state = State.PAYLOAD;
  }
}
