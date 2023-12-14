package cs3500.reversi.model;

/**
 * Represents a generic pair of two types, K and V.
 *
 * <p>This class is designed to store two related objects or values
 * of possibly different types: a key and a value.</p>
 *
 * @param <K> the type of the first element (key) of the pair
 * @param <V> the type of the second element (value) of the pair
 */
public class Pair<K, V> {
  private final K key;
  private final V value;

  /**
   * Initializes a new Pair with the given key and value.
   *
   * @param key the first element of the pair
   * @param value the second element of the pair
   */
  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Retrieves the key (first element) of this pair.
   *
   * @return the key associated with this pair
   */
  public K getKey() {
    return key;
  }

  /**
   * Retrieves the value (second element) of this pair.
   *
   * @return the value associated with this pair
   */
  public V getValue() {
    return value;
  }

  public String toString() {
    return "(q=" + key.toString() + ",r=" + value.toString() + ")\n";
  }
}
