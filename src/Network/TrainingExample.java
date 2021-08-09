package Network;

/*
 * Cameron Cronheimer
 * 6517080
 */
public class TrainingExample {

    double[] example; // the input
    double[] expectedOutput; // target
    double MSE; // Mean square error

    public TrainingExample(int size) {

        this.example = new double[size];

        // expectedOutput should be size of output layer
        this.expectedOutput = new double[1];

    }

    void setExample(double[] input) {

        this.example = input;
    }

    double[] getExample() {

        return example;
    }

    void setMSE(double MSE) {

        this.MSE = MSE;
    }

    double getMSE() {

        return MSE;

    }

    void setExpectedOutput(double[] expectedOutput) {

        this.expectedOutput = expectedOutput;
    }

    double[] getExpectedOutput() {

        return expectedOutput;
    }
}
