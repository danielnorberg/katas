package quirks;

import com.google.common.base.Strings;

public class SuppressedExceptions {

  public static void main(String[] args) {

    printHeading("failManualSuppression");
    try {
      failManualSuppression();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    printHeading("failManualClose");
    try {
      failManualClose();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    printHeading("failTryWithResources");
    try {
      failTryWithResources();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }

  private interface Failer {

    void fail() throws Exception;
  }

  private static void p(final Failer failer) {
    System.out.println(failer);
  }

  private static void printHeading(final String failManualSuppression) {
    System.out.println();
    System.out.println(Strings.repeat("-", 80));
    System.out.println(failManualSuppression);
    System.out.println();
  }

  private static void failManualSuppression() throws Exception {
    try {
      // This exception will be added to the list of suppressed exceptions
      throw new Exception("suppressed");
    } catch (Exception e) {
      final Exception outer = new Exception("outer");
      outer.addSuppressed(e);
      throw outer;
    }
  }

  private static void failManualClose() throws Exception {
    ThrowOnCloser toc = null;
    try {
      toc = new ThrowOnCloser();

      // This exception will be dropped
      throw new Exception("suppressed");
    } finally {
      if (toc != null) {
        toc.close();
      }
    }
  }

  private static void failTryWithResources() throws Exception {
    try (ThrowOnCloser toc = new ThrowOnCloser()) {
      // This exception will be added to the list of suppressed exceptions
      throw new Exception("suppressed");
    }
  }

  private static class ThrowOnCloser implements AutoCloseable {

    @Override
    public void close() throws Exception {
      throw new Exception("fail on close");
    }
  }
}
