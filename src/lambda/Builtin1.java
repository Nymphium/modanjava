package src.lambda;

import src.util.Pair;
import java.util.List;


public record Builtin1<V>(String name, Lambda<V> arg) implements Lambda<V> {
  public String toString() {
    return STR."\{this.name} \{this.arg}";
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) throws RuntimeException {
    return switch (this.name) {
      case "print" -> {
        var m = this.arg.eval(env);
        System.out.println(m);
        yield new Unit();
      }
      default -> { throw new RuntimeException(STR."unknown builtin1: \{this.name}"); }
    };
  }
}
