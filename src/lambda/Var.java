package src.lambda;

import src.util.Pair;
import java.util.List;

public record Var<V>(V name) implements Lambda<V> {
  public String toString() {
    return this.name.toString();
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) throws RuntimeException {
    var e = env.stream().filter(p -> p.first().equals(this.name)).findFirst();
    if (e.isPresent()) {
      return e.get().second();
    } else {
      throw new RuntimeException(STR."var \{this.name} not found");
    }
  }
}
