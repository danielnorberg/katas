package median;

public class Counter {

  private long v;

  public void inc() {
    v++;
  }

  public long value() {
    return v;
  }

  @Override
  public String toString() {
    return String.valueOf(v);
  }
}
