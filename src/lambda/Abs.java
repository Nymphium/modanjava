package src.lambda;

import src.util.Pair;
import java.util.List;

public record Abs<V>(V param, Lambda<V> body) implements Lambda<V> {
  public String toString() {
    return STR."λ\{this.param}.\{this.body}";
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) {
    return this;
  }
}
