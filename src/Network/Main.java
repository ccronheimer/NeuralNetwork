package Network;

import java.util.Arrays;

/*
 * Cameron Cronheimer
 * 6517080
 */
public class Main {

    Network net;
    TrainingExample[] trainingSet;

    // PARAMS
    double learningRate = 0.3;
    int numOfEpochs = 10000;
    int numOfHiddenNodes = 8;
    // ---------

    public Main() {

        // 16 possible examples/inputs
        trainingSet = new TrainingExample[16];

        // create the empty set
        for (int i = 0; i < trainingSet.length; i++) {

            trainingSet[i] = new TrainingExample(4);

        }

        // fill the set
        createSet();

        // 4 input, 8 hidden layers, 1 output
        int[] layerSizes = new int[] { 4, numOfHiddenNodes, 1 };
        net = new Network(layerSizes);

        // train the net with number of iterations and a learning rate
        trainNet(numOfEpochs, learningRate);

    }

    // All the possible examples/input for a 4 bit with respective output
    void createSet() {

        double[] in = new double[4];
        double[] out = new double[1];

        in = new double[] { 0, 0, 0, 0 };
        out = new double[] { 1 };
        trainingSet[0].setExample(in);
        trainingSet[0].setExpectedOutput(out);

        in = new double[] { 0, 0, 0, 1 };
        out = new double[] { 0 };
        trainingSet[1].setExample(in);
        trainingSet[1].setExpectedOutput(out);

        in = new double[] { 0, 0, 1, 0 };
        out = new double[] { 0 };
        trainingSet[2].setExample(in);
        trainingSet[2].setExpectedOutput(out);

        in = new double[] { 0, 0, 1, 1 };
        out = new double[] { 1 };
        trainingSet[3].setExample(in);
        trainingSet[3].setExpectedOutput(out);

        in = new double[] { 0, 1, 0, 0 };
        out = new double[] { 0 };
        trainingSet[4].setExample(in);
        trainingSet[4].setExpectedOutput(out);

        in = new double[] { 0, 1, 0, 1 };
        out = new double[] { 1 };
        trainingSet[5].setExample(in);
        trainingSet[5].setExpectedOutput(out);

        in = new double[] { 0, 1, 1, 0 };
        out = new double[] { 1 };
        trainingSet[6].setExample(in);
        trainingSet[6].setExpectedOutput(out);

        in = new double[] { 0, 1, 1, 1 };
        out = new double[] { 0 };
        trainingSet[7].setExample(in);
        trainingSet[7].setExpectedOutput(out);

        in = new double[] { 1, 0, 0, 0 };
        out = new double[] { 0 };
        trainingSet[8].setExample(in);
        trainingSet[8].setExpectedOutput(out);

        in = new double[] { 1, 0, 0, 1 };
        out = new double[] { 1 };
        trainingSet[9].setExample(in);
        trainingSet[9].setExpectedOutput(out);

        in = new double[] { 1, 0, 1, 0 };
        out = new double[] { 1 };
        trainingSet[10].setExample(in);
        trainingSet[10].setExpectedOutput(out);

        in = new double[] { 1, 0, 1, 1 };
        out = new double[] { 0 };
        trainingSet[11].setExample(in);
        trainingSet[11].setExpectedOutput(out);

        in = new double[] { 1, 1, 0, 0 };
        out = new double[] { 1 };
        trainingSet[12].setExample(in);
        trainingSet[12].setExpectedOutput(out);

        in = new double[] { 1, 1, 0, 1 };
        out = new double[] { 0 };
        trainingSet[13].setExample(in);
        trainingSet[13].setExpectedOutput(out);

        in = new double[] { 1, 1, 1, 0 };
        out = new double[] { 0 };
        trainingSet[14].setExample(in);
        trainingSet[14].setExpectedOutput(out);

        in = new double[] { 1, 1, 1, 1 };
        out = new double[] { 1 };
        trainingSet[15].setExample(in);
        trainingSet[15].setExpectedOutput(out);
    }

    void trainNet(int epochs, double learningRate) {

        for (int i = 0; i < epochs; i++) {

            // for all the examples in the trainingset
            for (int ex = 0; ex < trainingSet.length; ex++) {

                net.train(trainingSet[ex].getExample(), trainingSet[ex].getExpectedOutput(), learningRate);

                // set the mse to the ex obj so we can grab it in output
                trainingSet[ex].setMSE(net.MSE(trainingSet[ex].getExample(), trainingSet[ex].getExpectedOutput()));
            }

        }

        System.out.println();
        System.out.println("----------[OUTPUT]----------");
        System.out.println();

        System.out.println("Learning rate: " + learningRate + "|" + " Number of epochs: " + numOfEpochs + "|"
                + " Number of hidden nodes: " + numOfHiddenNodes);

        System.out.println();

        for (int i = 0; i < trainingSet.length; i++) {

            System.out.println("Training Example: " + Arrays.toString(trainingSet[i].getExample()) + "|" + " Expected: "
                    + Arrays.toString(trainingSet[i].getExpectedOutput()) + "|" + " MSE: " + trainingSet[i].getMSE()
                    + "|" + " Output: " + Arrays.toString(net.calculate(trainingSet[i].getExample())));

        }
    }

    public static void main(String[] args) {
        Main main = new Main();

    }

}
