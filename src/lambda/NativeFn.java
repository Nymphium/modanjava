package src.lambda;

import src.util.Pair;
import java.util.List;
import java.util.function.Function;

public record NativeFn<V>(Function<Lambda<V>, Lambda<V>> fn) implements Lambda<V> {
  public String toString() {
    return "<native function>";
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) {
    return this;
  }
}
