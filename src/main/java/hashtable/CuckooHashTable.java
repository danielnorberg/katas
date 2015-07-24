package hashtable;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class CuckooHashTable<K, V> extends AbstractMap<K, V> {

  private Object[] table;

  private int capacity;
  private int size;
  private int mask;

  private int seed = 0;

  private HashFunction hasher;

  public CuckooHashTable() {
    clear();
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    return new EntrySet();
  }

  @Override
  public V put(final K key, final V value) {
    final int ix1 = ix1(key);
    final K key1 = key(ix1);

    // First slot empty?
    if (key1 == null) {
      size++;
      setEntry(ix1, key, value);
      return null;
    }

    // Replace first slot value?
    if (key1.equals(key)) {
      return setValue(ix1, value);
    }

    // Second slot empty?
    final int ix2 = ix2(ix1);
    final K key2 = key(ix2);
    if (key2 == null) {
      size++;
      setEntry(ix2, key, value);
      return null;
    }

    // Replace second slot value?
    if (key2.equals(key)) {
      return setValue(ix2, value);
    }

    // Evict second slot
    move(ix2, key2);
    return put(key, value);
  }

  @Override
  public V get(final Object key) {
    // First slot?
    final int ix1 = ix1(key);
    final K key1 = key(ix1);
    if (key1 != null && key1.equals(key)) {
      return value(ix1);
    }

    // Second slot?
    final int ix2 = ix2(ix1);
    final K key2 = key(ix2);
    if (key2 != null && key2.equals(key)) {
      return value(ix2);
    }

    // Miss
    return null;
  }

  @Override
  public V remove(final Object key) {
    // First slot?
    final int ix1 = ix1(key);
    final K key1 = key(ix1);
    if (key1 != null && key1.equals(key)) {
      return remove(ix1);
    }

    // Second slot?
    final int ix2 = ix2(ix1);
    final K key2 = key(ix2);
    if (key2 != null && key2.equals(key)) {
      return remove(ix2);
    }

    // Miss
    return null;
  }

  @SuppressWarnings("unchecked")
  private K key(final int ix) {
    return (K) table[ix << 1];
  }

  @SuppressWarnings("unchecked")
  private V value(final int ix) {
    return (V) table[(ix << 1) + 1];
  }

  private void setEntry(final int ix, final K key, final V value) {
    final int rix = ix << 1;
    table[rix] = key;
    table[rix + 1] = value;
  }

  private V setValue(final int ix, final V value) {
    final int rix = ix << 1;
    @SuppressWarnings("unchecked") final V prev = (V) table[rix + 1];
    table[rix + 1] = value;
    return prev;
  }

  private V remove(final int ix) {
    final K key = key(ix);
    if (key == null) {
      throw new IllegalStateException();
    }
    final V value = value(ix);
    final int rix = ix << 1;
    table[rix] = null;
    table[rix + 1] = null;
    size--;
    return value;
  }

  @Override
  public void clear() {
    initHash();
    size = 0;
    capacity = 16;
    mask = capacity - 1;
    table = new Object[capacity * 2];
  }

  private void initHash() {
    hasher = Hashing.murmur3_32(seed++);
  }

  private boolean move(final int ix, final K key) {
    final int ix1 = ix1(key);
    if (ix != ix1) {
      // Entry is already in alternate position. Make more space.
      resize();
      return true;
    }

    final V value = value(ix1);
    final int ix2 = ix2(ix1);
    final K key2 = key(ix2);
    if (key2 != null) {
      if (move(ix2, key2)) {
        return true;
      }
    }
    setEntry(ix2, key, value);
    return false;
  }

  private void resize() {
    initHash();
    final Object[] old = table;
    capacity = capacity * 2;
    table = new Object[capacity * 2];
    size = 0;
    for (int i = 0; i < old.length; i += 2) {
      @SuppressWarnings("unchecked") final K key = (K) old[i];
      if (key != null) {
        @SuppressWarnings("unchecked") final V value = (V) old[i + 1];
        put(key, value);
      }
    }
  }

  private int ix1(final Object key) {
    final int h = key.hashCode();
    return hasher.hashInt(h).asInt() & mask;
  }

  private int ix2(final int ix1) {
    return hasher.hashInt(ix1).asInt() & mask;
  }

  private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
      return new EntrySetIterator();
    }

    @Override
    public int size() {
      return size;
    }

    @Override
    public void clear() {
      CuckooHashTable.this.clear();
    }
  }

  private class EntrySetIterator implements Iterator<Map.Entry<K, V>> {

    int curr = -1;
    int next = -1;

    public EntrySetIterator() {
      seek();
    }

    private void seek() {
      while (true) {
        next++;
        if (next >= capacity) {
          return;
        }
        final K key = key(next);
        if (key != null) {
          return;
        }
      }
    }

    @Override
    public boolean hasNext() {
      return next > 0 && next < capacity;
    }

    @Override
    public Map.Entry<K, V> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      curr = next;
      seek();
      return new Entry(curr);
    }

    @Override
    public void remove() {
      if (curr < 0 || key(curr) == null) {
        throw new IllegalStateException();
      }
      CuckooHashTable.this.remove(curr);
    }
  }

  private class Entry implements Map.Entry<K, V> {

    private final int ix;

    public Entry(final int ix) {
      this.ix = ix;
    }

    @Override
    public K getKey() {
      return key(ix);
    }

    @Override
    public V getValue() {
      return value(ix);
    }

    @Override
    public V setValue(final V value) {
      return CuckooHashTable.this.setValue(ix, value);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      @SuppressWarnings("unchecked") final Entry entry = (Entry) o;

      return Objects.equals(getKey(), entry.getKey()) &&
             Objects.equals(getValue(), entry.getValue());
    }

    @Override
    public int hashCode() {
      final V value = getValue();
      return getKey().hashCode() ^ (value == null ? 0 : value.hashCode());
    }
  }
}
