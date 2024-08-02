package nn;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Value {
  public double data;
  public double grad;

  private Value[] children;
  public Runnable backward;

  public Value(double data, Value[] children) {
    this.data = data;
    this.grad = 0;
    this.children = children;
    this.backward = () -> {
    };
  }

  public Value(double data) {
    this(data, new Value[] {});
  }

  public Value add(Value other) {
    Value out = new Value(this.data + other.data, new Value[] { this, other });

    out.backward = () -> {
      this.grad += out.grad;
      other.grad += out.grad;
    };

    return out;
  }

  public Value mul(Value other) {
    Value out = new Value(this.data * other.data, new Value[] { this, other });

    out.backward = () -> {
      this.grad += other.data * out.grad;
      other.grad += this.data * out.grad;
    };

    return out;
  }

  public Value pow(double exponent) {
    Value out = new Value(Math.pow(this.data, exponent), new Value[] { this });

    out.backward = () -> {
      this.grad += (exponent * Math.pow(this.data, exponent - 1)) * out.grad;
    };

    return out;
  }

  public Value exp(double x) {
    Value out = new Value(Math.exp(x), new Value[] { this });

    out.backward = () -> {
      this.grad += Math.exp(this.data) * out.grad;
    };

    return out;
  }

  public Value relu() {
    Value out = new Value(this.data > 0 ? this.data : 0, new Value[] { this });
    out.backward = () -> {
      this.grad += (out.data > 0 ? 1 : 0) * out.grad;
    };
    return out;
  }

  public Value neg() {
    return this.mul(new Value(-1));
  }

  public Value sub(Value other) {
    return this.add(other.neg());
  }

  public Value div(Value other) {
    return this.mul(other.pow(-1));
  }

  public void backward() {
    Set<Value> visited = new HashSet<>();
    Stack<Value> topo = new Stack<>();
    buildTopo(this, visited, topo);

    this.grad = 1;
    while (!topo.isEmpty()) {
      topo.pop().backward.run();
    }
  }

  private void buildTopo(Value v, Set<Value> visited, Stack<Value> topo) {
    if (!visited.contains(v)) {
      visited.add(v);
      for (Value child : v.children) {
        buildTopo(child, visited, topo);
      }
      topo.push(v);
    }
  }

  @Override
  public String toString() {
    return String.format("Value(data=%.2f, grad=%.2f)", this.data, this.grad);
  }
}
