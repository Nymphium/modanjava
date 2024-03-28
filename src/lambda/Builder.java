package src.lambda;

import java.util.function.Function;

public interface Builder {
  public static <V> Lambda<V> unit() { return new Unit(); }
  public static <V> Lambda<V> var(V name) { return new Var(name); }
  public static <V> Lambda<V> abs(V param, Lambda<V> body) { return new Abs(param, body); }
  public static <V> Lambda<V> app(Lambda<V> func, Lambda<V> arg) { return new App(func, arg); }
  public static <V> Lambda<V> builtin1(String name, Lambda<V> arg) { return new Builtin1(name, arg); }
  public static <V> Lambda<V> print(Lambda<V> arg) { return new Builtin1("print", arg); }
  public static <V> Lambda<V> nativefn(Function<Lambda<V>, Lambda<V>> fn) { return new NativeFn(fn); }
}
