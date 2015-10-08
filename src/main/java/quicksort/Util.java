package quicksort;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

public class Util {

  public static final int[] ANTI_QSORT_10K = ints("antiquicksort10K.txt.gz");
  public static final int[] ANTI_QSORT_1M = ints("antiquicksort1M.txt.gz");

  public static final int[] IDENTICAL_17_1K = IntStream.range(0, 1000).map(i -> 17).toArray();
  public static final int[] MOSTLY_IDENTICAL_17_1K = IntStream.range(0, 1000)
      .map(i -> ThreadLocalRandom.current().nextInt(7) == 0 ? ThreadLocalRandom.current().nextInt() : 17)
      .toArray();

  private static int[] ints(final String resource) {
    final URL url = Util.class.getResource(resource);
    final List<String> lines;
    try (
        final InputStream stream = new GZIPInputStream(url.openStream());
        final InputStreamReader reader = new InputStreamReader(stream, "UTF-8")
    ) {
      lines = CharStreams.readLines(reader);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
    return lines.stream()
        .mapToInt(Integer::parseInt)
        .toArray();
  }
}
