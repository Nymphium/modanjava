package src.lambda;

import static src.lambda.Lambda.*;
import java.util.function.Function;

public interface Builder {
  public static <V> T<V> unit() { return new Unit(); }
  public static <V> T<V> var(V name) { return new Var(name); }
  public static <V> T<V> abs(V param, T<V> body) { return new Abs(param, body); }
  public static <V> T<V> app(T<V> func, T<V> arg) { return new App(func, arg); }
  public static <V> T<V> builtin1(String name, T<V> arg) { return new Builtin1(name, arg); }
  public static <V> T<V> print(T<V> arg) { return new Builtin1("print", arg); }
  public static <V> T<V> nativefn(Function<T<V>, T<V>> fn) { return new NativeFn(fn); }
}
