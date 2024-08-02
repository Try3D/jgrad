package nn;

public class Layer {
  private Neuron[] neurons;

  public Layer(int nin, int nout, boolean nonlin) {
    neurons = new Neuron[nout];
    for (int i = 0; i < nout; i++) {
      neurons[i] = new Neuron(nin, nonlin);
    }
  }

  public Layer(int nin, int nout) {
    this(nin, nout, true);
  }

  public Value[] forward(Value[] x) {
    Value[] out = new Value[neurons.length];
    for (int i = 0; i < neurons.length; i++) {
      out[i] = neurons[i].forward(x);
    }
    return out.length == 1 ? new Value[] { out[0] } : out;
  }

  public Value[] parameters() {
    int numParams = 0;
    for (Neuron n : neurons) {
      numParams += n.parameters().length;
    }

    Value[] params = new Value[numParams];
    int index = 0;
    for (Neuron n : neurons) {
      Value[] neuronParams = n.parameters();
      for (Value p : neuronParams) {
        params[index++] = p;
      }
    }
    return params;
  }

  public void zeroGrad() {
    for (Value p : parameters()) {
      p.grad = 0;
    }
  }
}
