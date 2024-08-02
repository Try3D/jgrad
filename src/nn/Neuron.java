package nn;

import java.util.Random;

public class Neuron {
  public int nin;
  public Value[] w;
  public Value b;
  private boolean nonlin;

  public Neuron(int nin, boolean nonlin) {
    this.nin = nin;
    this.nonlin = nonlin;

    Random rand = new Random();

    this.w = new Value[nin];

    for (int i = 0; i < nin; i++) {
      w[i] = new Value(rand.nextDouble());
    }

    b = new Value(0);
  }

  public Neuron(int nin) {
    this(nin, true);
  }

  public Value forward(Value[] x) {
    if (x.length != nin) {
      throw new IllegalArgumentException("Input array length must be equal to the number of inputs of the neuron");
    }

    Value sum = b;
    for (int i = 0; i < nin; i++) {
      sum = sum.add(w[i].mul(x[i]));
    }

    if (nonlin) {
      return sum.relu();
    } else {
      return sum;
    }
  }

  public Value[] parameters() {
    Value[] params = new Value[nin + 1];

    System.arraycopy(w, 0, params, 0, nin);
    params[nin] = b;

    return params;
  }

  public void zeroGrad() {
    for (int i = 0; i < nin; i++) {
      w[i].grad = 0;
    }
  }
}
