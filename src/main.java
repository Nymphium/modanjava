package src;

import static src.lambda.Builder.*;

import src.util.Pair;
import src.lambda.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// Implicitly Declared Classes and Instance Main Methods
// (Previously "Unnamed Classes and Instance Main Methods" Java 21, 2nd preview) - Java 22
void main() {
  var id = abs("x", var("x"));
  System.out.println(id);

  var two = abs("f", abs("x", app(var("f"), app(var("f"), var("x")))));
  var three = abs("f", abs("x", app(var("f"), app(var("f"), app(var("f"), var("x"))))));
  var plus = abs("m", abs("n", abs("f", abs("x", app(app(var("m"), var("f")), app(app(var("n"), var("f")), var("x")))))));
  builtin1("print", app(app(plus, two), three)).eval(new ArrayList<>());

  var print = new Pair<String,Lambda<String>>("print",
          nativefn(a -> {
              System.out.println(a);
              return unit();
          }));

  var env = List.of(print);

  app(var(print.first()), app(app(plus, two), three)).eval(env);
}
