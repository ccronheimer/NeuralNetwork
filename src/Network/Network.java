package Network;

/*
 * Cameron Cronheimer
 * 6517080
 */

public class Network {

    double[][] output;
    double weights[][][]; // weights[layer][neuron][prevNeuron]
    double[][] bias;

    double[][] errorSignal; //gradient
    double[][] outputDerivative;

    int[] layerSizes; // [4,8,1]
    int inputSize; // 4
    int outputSize; // 1
    int netSize; // 3

    int iteration = 0;
    int c;

    public Network(int[] layerSizes) {

        this.layerSizes = layerSizes;
        this.inputSize = layerSizes[0]; // input = first layer
        this.netSize = layerSizes.length;
        this.outputSize = layerSizes[netSize - 1]; // output = last layer

        this.output = new double[netSize][];
        this.weights = new double[netSize][][]; // weights[layer][neuron][prevNeuron]
        this.bias = new double[netSize][];

        this.errorSignal = new double[netSize][];
        this.outputDerivative = new double[netSize][];

        // Intilialize
        for (int i = 0; i < netSize; i++) {

            this.output[i] = new double[layerSizes[i]];
            this.errorSignal[i] = new double[layerSizes[i]];
            this.outputDerivative[i] = new double[layerSizes[i]];

            // sets the bias b/w low and high
            this.bias[i] = createRandomArray(layerSizes[i], -0.3, 0.7);

            if (i > 0) {

                // set the random weights
                weights[i] = createRandomWeights(layerSizes[i], layerSizes[i - 1], -1, 1);

            }
        }
    }

    // returns array[size] with random doubles b/w low and high
    public static double[] createRandomArray(int size, double low, double high) {

        double[] array = new double[size];

        for (int i = 0; i < size; i++) {

            array[i] = (Math.random() * (high - low) + low);

        }
        return array;
    }

    // returns 2d array with random doubles b/w low and high for net
    // weights
    public static double[][] createRandomWeights(int width, int height, double low, double high) {

        double[][] array = new double[width][height];

        for (int i = 0; i < width; i++) {

            array[i] = createRandomArray(height, low, high);

        }

        return array;
    }

    public double[] calculate(double[] input) {

        this.output[0] = input;

        //move through the networks layers
        for (int layer = 1; layer < netSize; layer++) {

            //iterate nuerons in each layer
            for (int neuron = 0; neuron < layerSizes[layer]; neuron++) {

                //add bias first
                double sum = bias[layer][neuron];

                //iterate through neurons in previous layer
                for (int prevNeuron = 0; prevNeuron < layerSizes[layer - 1]; prevNeuron++) {

                    //multiply previous neurons values by current nuerons weight
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];

                }

                //calc sigmoid
                //used for backpropagation after
                output[layer][neuron] = sigmoid(sum);

                //calc derivative
                outputDerivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);

            }
        }

        return output[netSize - 1];
    }

    //called from Main
    public void train(double[] input, double[] expectedOutput, double learningRate) {

        calculate(input);
        backpropagation(expectedOutput);
        updateWeights(learningRate);

        iteration += 1;
        c += 1;

        if (c == 200) {

            System.out.println("ITERATION: " + iteration + " MSE: " + MSE(input, expectedOutput));

            c = 0;
        }

    }

    //Mean Squared Error
    public double MSE(double[] input, double[] expectedOutput) {

        double sum = 0;

        for (int i = 0; i < expectedOutput.length; i++) {

            // sum += (expectedOutput - currentOutput) ^ 2
            sum += (expectedOutput[i] - output[netSize - 1][i]) * (expectedOutput[i] - output[netSize - 1][i]);
        }

        //sum / 2 * 1(expectedOutputLength)
        return (sum / (2d * expectedOutput.length));

    }

    //back propagation
    public void backpropagation(double[] expectedOutput) {

        for (int neuron = 0; neuron < layerSizes[netSize - 1]; neuron++) {

            //we need to calculate the error signal by multiplying the derivative of the sigmoid
            //sigmoid would be held in the output currently
            errorSignal[netSize - 1][neuron] = (output[netSize - 1][neuron] - expectedOutput[neuron]) * outputDerivative[netSize - 1][neuron];
        }

        //move back in the net
        for (int layer = netSize - 2; layer > 0; layer--) {

            //iterate through nuerons
            for (int neuron = 0; neuron < layerSizes[layer]; neuron++) {

                double sum = 0;

                //iterate through the next neurons unlike the previous neurons
                for (int nextNeuron = 0; nextNeuron < layerSizes[layer + 1]; nextNeuron++) {

                    sum += weights[layer + 1][nextNeuron][neuron] * errorSignal[layer + 1][nextNeuron];

                }

                this.errorSignal[layer][neuron] = sum * outputDerivative[layer][neuron];
            }
        }
    }

    //re-adjust rates by learning rate
    //learning process
    public void updateWeights(double learningRate) {

        for (int layer = 1; layer < netSize; layer++) {

            for (int neuron = 0; neuron < layerSizes[layer]; neuron++) {

                //delta = -learning rate * error score of neuron
                double delta = -learningRate * errorSignal[layer][neuron];

                //add the bias to delta
                bias[layer][neuron] += delta;

                //readjust previous neurons by adding delta (learning)
                for (int prevNeuron = 0; prevNeuron < layerSizes[layer - 1]; prevNeuron++) {

                    weights[layer][neuron][prevNeuron] += delta * output[layer - 1][prevNeuron];

                }
            }
        }
    }

    //sigmoid function
    private double sigmoid(double x) {

        return (1d / (1 + Math.exp(-x)));

    }

}