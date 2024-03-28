package src.lambda;

import src.util.Pair;
import java.util.List;

public record Unit<V>() implements Lambda<V> {
  public String toString() {
    return "()";
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) {
    return this;
  }
}
