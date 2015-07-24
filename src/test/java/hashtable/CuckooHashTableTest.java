package hashtable;

import com.google.common.collect.testing.MapInterfaceTest;

import java.util.Map;

public class CuckooHashTableTest extends MapInterfaceTest<String, Integer> {

  public CuckooHashTableTest() {
    super(false, true, true, true, true, true);
  }

  @Override
  protected Map<String, Integer> makeEmptyMap() throws UnsupportedOperationException {
    return new CuckooHashTable<>();
  }

  @Override
  protected Map<String, Integer> makePopulatedMap() throws UnsupportedOperationException {
    final Map<String, Integer> map = new CuckooHashTable<>();
    for (int i = 0; i < 4; i++) {
      map.put("key-" + i, i);
    }
    return map;
  }

  @Override
  protected String getKeyNotInPopulatedMap() throws UnsupportedOperationException {
    return "not-present";
  }

  @Override
  protected Integer getValueNotInPopulatedMap() throws UnsupportedOperationException {
    return 4711;
  }
}