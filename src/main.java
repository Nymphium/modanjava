import static src.lambda.Builder.*;

import src.util.Pair;
import src.lambda.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

void main() {
  var id = abs("x", var("x"));
  System.out.println(id);

  var two = abs("f", abs("x", app(var("f"), app(var("f"), var("x")))));
  var three = abs("f", abs("x", app(var("f"), app(var("f"), app(var("f"), var("x"))))));
  var plus = abs("m", abs("n", abs("f", abs("x", app(app(var("m"), var("f")), app(app(var("n"), var("f")), var("x")))))));
  var app = builtin1("print", app(app(plus, two), three));

  app.eval(new ArrayList<>());

  List<Pair<String, Lambda.T<String>>> env = List.of(new Pair<>("print", nativefn(a -> {
          System.out.println(a);
          return unit();
        })));

  app(var("print"), app(app(plus, two), three)).eval(env);
}
