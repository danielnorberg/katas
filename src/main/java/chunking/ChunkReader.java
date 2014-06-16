package chunking;

import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Stream;

public class ChunkReader {

  private enum State {
    HEADER,
    PAYLOAD
  }

  private final ByteBuffer header = ByteBuffer.allocate(4);

  private State state = State.HEADER;
  private ByteBuffer message;

  public Stream<ByteBuffer> inject(final ByteBuffer chunk) {
    final List<ByteBuffer> messages = Lists.newArrayList();
    if (chunk == null) {
      messages.add(null);
    } else {
      while (chunk.hasRemaining()) {
        switch (state) {
          case HEADER:
            header(chunk);
            break;
          case PAYLOAD:
            payload(chunk, messages);
            break;
        }
      }
    }
    return messages.stream();
  }

  private void payload(final ByteBuffer chunk, final List<ByteBuffer> messages) {
    final int n = Math.min(message.remaining(), chunk.remaining());
    message.put(readSlice(n, chunk));
    if (!message.hasRemaining()) {
      final ByteBuffer completed = message;
      message = null;
      state = State.HEADER;
      completed.flip();
      messages.add(completed);
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
