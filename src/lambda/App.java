package src.lambda;

import src.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record App<V>(Lambda<V> func, Lambda<V> arg) implements Lambda<V> {
  public String toString() {
    // string templates (2nd preview) - Java 21 (1st preview)
    return STR."(\{this.func} \{this.arg})";
  }

  public Lambda<V> eval(List<Pair<V, Lambda<V>>> env) throws RuntimeException {
    var func = this.func.eval(env);
    var arg = this.arg.eval( env);
    // switch expression - Java 14
    return switch (func) {
      // pattern matching - Java 21
      case Abs(V param, var body) -> {
        var newEnv = new ArrayList<>(env);
        newEnv.add(new Pair<>(param, arg));
        yield body.eval(newEnv);
      }
      case NativeFn(Function<Lambda<V>, Lambda<V>> fn) -> fn.apply(arg);
      default -> { throw new RuntimeException(STR."not a function: \{func}"); }
    };
  }
}
