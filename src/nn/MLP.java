package nn;

public class MLP {
  private Layer[] layers;

  public MLP(int[] nlayers) {
    int numLayers = nlayers.length - 1;
    layers = new Layer[numLayers];

    for (int i = 0; i < numLayers; i++) {
      boolean nonlin = i != numLayers - 1;
      layers[i] = new Layer(nlayers[i], nlayers[i + 1], nonlin);
    }
  }

  public Value[] forward(Value[] x) {
    for (Layer layer : layers) {
      x = layer.forward(x);
    }
    return x;
  }

  public Value[] parameters() {
    int totalParams = 0;
    for (Layer layer : layers) {
      totalParams += layer.parameters().length;
    }

    Value[] params = new Value[totalParams];
    int index = 0;
    for (Layer layer : layers) {
      Value[] layerParams = layer.parameters();
      System.arraycopy(layerParams, 0, params, index, layerParams.length);
      index += layerParams.length;
    }
    return params;
  }

  public void zeroGrad() {
    for (Value p : parameters()) {
      p.grad = 0;
    }
  }
}
