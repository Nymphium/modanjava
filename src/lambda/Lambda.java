package src.lambda;

import src.util.Pair;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public interface  Lambda {
  public sealed interface T<V>
    permits Unit, Var, Abs, App, Builtin1, NativeFn {
      public T<V> eval(List<Pair<V, T<V>>> env);
    }

  public record Unit<V>() implements T<V> {
    public String toString() {
      return "()";
    }

    public T<V> eval(List<Pair<V, T<V>>> env) {
      return this;
    }
  }

  public record Var<V>(V name) implements T<V> {
    public String toString() {
      return this.name.toString();
    }

    public T<V> eval(List<Pair<V, T<V>>> env) throws RuntimeException {
      var e = env.stream().filter(p -> p.first().equals(this.name)).findFirst();
      if (e.isPresent()) {
        return e.get().second();
      } else {
        throw new RuntimeException(STR."var \{this.name} not found");
      }
    }
  }

  public record Abs<V>(V param, T<V> body) implements T<V> {
    public String toString() {
      return STR."λ\{this.param}.\{this.body}";
    }

    public T<V> eval(List<Pair<V, T<V>>> env) {
      return this;
    }
  }

  public record App<V>(T<V> func, T<V> arg) implements T<V> {
    public String toString() {
      return STR."(\{this.func} \{this.arg})";
    }

    public T<V> eval(List<Pair<V, T<V>>> env) throws RuntimeException {
      var func = this.func.eval(env);
      var arg = this.arg.eval( env);
      return switch (func) {
        case Abs(V param, var body) -> {
          var newEnv = new ArrayList<>(env);
          newEnv.add(new Pair<>(param, arg));
          yield body.eval(newEnv);
        }
        case NativeFn(Function<T<V>, T<V>> fn) -> fn.apply(arg);
        default -> { throw new RuntimeException(STR."not a function: \{func}"); }
      };
    }
  }

  public record Builtin1<V>(String name, T<V> arg) implements T<V> {
    public String toString() {
      return STR."\{this.name} \{this.arg}";
    }

    public T<V> eval(List<Pair<V, T<V>>> env) throws RuntimeException {
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

  public record NativeFn<V>(Function<T<V>, T<V>> fn) implements T<V> {
    public String toString() {
      return "<native function>";
    }

    public T<V> eval(List<Pair<V, T<V>>> env) {
      return this;
    }
  }

  // public static String show(T l) {
    // return switch (l) {
      // case Unit u -> "()";
        // case Var v -> v.name;
      // case Abs a -> "λ" + a.param + "." + show(a.body);
      // case App a -> "(" + show(a.func) + " " + show(a.arg) + ")";
      // case Builtin1 b -> b.name + " " + show(b.arg);
    // };
  // }

  // public static T eval(T t, List<Pair<String, T>> env) throws RuntimeException {
    // return switch (t) {
      // case Unit u -> u;
      // case Var v -> {
        // var e = env.stream().filter(p -> p.first().equals(v.name)).findFirst();
        // yield e.isPresent() ? e.get().second() : v;
      // }
      // case Abs a -> a;
      // case App a -> {
        // var func = eval(a.func, env);
        // var arg = eval(a.arg, env);
        // switch (func) {
          // case Abs abs -> {
            // var newEnv = new ArrayList<>(env);
            // newEnv.add(new Pair<>(abs.param, arg));
            // yield eval(abs.body, newEnv);
          // }
          // default -> { throw new RuntimeException(STR."not a function: \{func}"); }
        // }
      // }
      // case Builtin1 b -> {
        // switch (b.name) {
          // case "print" -> {
            // var m = eval(b.arg, env);
            // System.out.println(show(m));
            // yield unit();
          // }
          // default -> { throw new RuntimeException(STR."unknown builtin1: \{b.name}"); }
        // }
      // }
    // };
  // }
}
