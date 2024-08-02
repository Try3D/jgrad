import nn.Value;

public class Main {
  public static void main(String[] args) {
    Value w = new Value(1);
    Value b = new Value(0);

    Datasets ds = new Datasets();

    for (int epoch = 0; epoch < 10000; epoch++) {
      double loss = 0;
      for (int i = 0; i < ds.trainX.length; i++) {
        w.grad = 0;
        b.grad = 0;

        Value out = ds.trainX[i].mul(w).add(b);

        Value cost = out.pow(2).sub(ds.trainY[i].pow(2));
        if (cost.data < 0) {
          cost = cost.neg();
        }

        loss += cost.data;
        cost.backward();

        w.data -= w.grad * 0.0001;
        b.data -= b.grad * 0.0001;
      }
      if (epoch % 100 == 9) {
        System.out.println("Epoch: " + (epoch + 1) + " Loss: " + loss);
      }
    }

    System.out.println("w: " + w);
    System.out.println("b: " + b);
  }
}
