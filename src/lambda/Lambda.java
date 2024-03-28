package src.lambda;

import src.util.Pair;
import java.util.List;

public sealed interface Lambda <V>
    permits Unit, Var, Abs, App, Builtin1, NativeFn {
      public Lambda<V> eval(List<Pair<V, Lambda<V>>> env);
}
