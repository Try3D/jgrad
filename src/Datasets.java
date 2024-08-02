import nn.Value;

public class Datasets {
  public Value[] trainX;
  public Value[] trainY;

  public Value[] testX;
  public Value[] testY;

  public Datasets() {
    double w = 0.7;
    double b = 0.3;

    trainX = new Value[12];
    trainY = new Value[12];
    for (int i = 0; i < 12; i++) {
      trainX[i] = new Value(i);
      trainY[i] = new Value(i * w + b);
    }

    testX = new Value[12];
    testY = new Value[4];
    for (int i = 0; i < 4; i++) {
      testX[i] = new Value(i + 12);
      testY[i] = new Value((i + 12) * w + b);
    }
  }
}
