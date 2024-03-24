import src.lambda.*;
import java.util.ArrayList;

void main() {
  var id = Lambda.abs("x", Lambda.v("x"));
  System.out.println(id);

  var two = Lambda.abs("f", Lambda.abs("x", Lambda.app(Lambda.v("f"), Lambda.app(Lambda.v("f"), Lambda.v("x")))));
  var three = Lambda.abs("f", Lambda.abs("x", Lambda.app(Lambda.v("f"), Lambda.app(Lambda.v("f"), Lambda.app(Lambda.v("f"), Lambda.v("x"))))));
  var plus = Lambda.abs("m", Lambda.abs("n", Lambda.abs("f", Lambda.abs("x", Lambda.app(Lambda.app(Lambda.v("m"), Lambda.v("f")), Lambda.app(Lambda.app(Lambda.v("n"), Lambda.v("f")), Lambda.v("x")))))));
  var app = Lambda.builtin1("print", Lambda.app(Lambda.app(plus, two), three));

  // Lambda.eval(app, new ArrayList<>());
  app.eval(new ArrayList<>());
}
