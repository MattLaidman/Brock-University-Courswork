import java.util.Random;

/**
 * ParityCheck class performs the actual neural network operations using the input
 * arguments passed by runParityCheck class.
 *
 * Weights are initialized randomly from the seed in the constructor, and so are the number
 * of hidden nodes. After the run procedure has been called the sample data is learned for
 * the passed amount of epochs and then finally the data is passed through the network one
 * final time to be tested.
 */

public class ParityCheck {

    private double learningRate;
    private int hiddenNodes;
    private int epochs;
    private long seed;
    private double[] values; // 4 input + n hidden
    private double[] sums;
    private double[][] weights; // 2D adjacency matrix for weights
    private double[] errors; // 2D adjacency matrix for errors passed back
    private Random rnd; // Pseudo-RNG


    public ParityCheck (double learningRate, int hiddenNodes, int epochs, long seed) {

        // Array for storing values to be passed through to each layer
        values = new double[4 + hiddenNodes]; // 4 input + n hidden
        sums = new double[4 + hiddenNodes + 1];
        weights = new double[4 + hiddenNodes + 1] // 2D adjacency matrix for weights
                [4 + hiddenNodes + 1];
        errors = new double[4 + hiddenNodes + 1]; // 2D adjacency matrix for errors
        rnd = new Random(seed); // Pseudo-RNG from SEED
        this.learningRate = learningRate;
        this.hiddenNodes = hiddenNodes;
        this.epochs = epochs;
        this.seed = seed;

        initWeights();
    } // Constructor

    // run procedure initially runs the training data through the network for the desired
    // epochs. Then the data is run through one final time to be tested.
    public void run ( ) {

        System.out.println("Parameters:\n");
        System.out.println("Learning Rate\t- " + learningRate);
        System.out.println("Hidden Nodes\t- " + hiddenNodes);
        System.out.println("Epochs\t\t- " + epochs);
        System.out.println("Seed\t\t- " + seed);

        System.out.println("\nTraining:");
        memorizeData(learningRate, epochs);

        System.out.println("\nTesting:");
        runNetwork();
    }

    // Runs the data through the network outputting the values produced
    private void runNetwork ( ) {

        double parity;
        String[] sampleInputs = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
                "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"}; // Sample data!

        System.out.println();
        for (String s : sampleInputs) {
            parity = feedForward(s);
            System.out.println(s + " - " + Math.round(parity)+ " (" + parity + ")");
        }

    }

    // Performs the memorization on the training set
    private void memorizeData (double learningRate, int epochs) {

        String[] trainingInputs = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
                "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"}; // Training data!
        int[] trainingOutputs = {1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1};
        double output;

        for (int e = 0 ; e < epochs ; e++) { // Repeat for number of epochs
            System.out.println("\nEpoch: " + (e+1));
            for (int t = 0 ; t < trainingInputs.length ; t++) { // For each sample from training data
                output = feedForward(trainingInputs[t]);
                System.out.println(trainingInputs[t] + " - " + output);
                passBack(trainingOutputs[t] - output, learningRate); // Backwards pass of the error
                //printWeights();
            }
        }
    }

    // Performs the backwards pass of the error throughout the network
    private void passBack (double error, double learningRate) {

        int o = 4 + hiddenNodes; // output node index

        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // For each hidden node
            errors[h] = weights[h][o]*error; // Calculate error
        }
        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // For each input node
            for (int i = 0 ; i < 4 ; i++) { // update weights on passes to hidden nodes
                weights[i][h] = weights[i][h] + learningRate*errors[h]*activatePrime(sums[h])*values[i];
                //System.out.println("[" + h + "] - " + sums[h] + " | " + activatePrime(sums[h]) +  " | " + values[i]);
            }
        }
        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // for each hidden node
            weights[h][o] = weights[h][o] + learningRate*error*activatePrime(sums[o])*values[h]; // update weights on passes to output node
        }
    }

    // Performs the actual feed-forward calculations
    private double feedForward (String word) {

        int o = 4 + hiddenNodes; // output index

        values[0] = (double)(word.charAt(0) - '0'); // "Load" input values into input nodes
        values[1] = (double)(word.charAt(1) - '0');
        values[2] = (double)(word.charAt(2) - '0');
        values[3] = (double)(word.charAt(3) - '0');

        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // For each hidden node
            sums[h] = values[0]*weights[0][h] + values[1]*weights[1][h] + values[2]*weights[2][h] +
                    values[3]*weights[3][h];
            values[h] = activate(sums[h]); // Calculate value to be passed
        }
        sums[o] = 0;
        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // For each hidden node
            sums[o] = sums[o] + values[h]*weights[h][o]; // get sum for output
        }
        return activate(sums[o]);
    }

    // Activation function (Sigmoid)
    private double activate (double t) { return 1/(1+Math.exp(-t)); }

    // Activation function derivative (Sigmoid)
    private double activatePrime(double t) { return activate(t)*(1-activate(t)); }

    // Returns an initialized weighted adjacency matrix
    private void initWeights ( ) {

        int o = 4 + hiddenNodes; // output index

        for (int i = 0 ; i < 4 ; i++) { // For each input node
            for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // Add initial weight to connection to each hidden node
                weights[i][h] = getRandomInitVal();
            }
        }
        for (int h = 4 ; h < 4 + hiddenNodes ; h++) { // for each hidden node
            weights[h][o] = getRandomInitVal(); // Add initial weight to connection to output node
        }
    }

    // Returns a value between -0.5 and 0.5
    private double getRandomInitVal ( ) {

        return (rnd.nextDouble() - 0.5);
    }

    // Function used in debugging for
    @SuppressWarnings("unused")
    public void printWeights()
    {

        for (double[] wa : weights) {
            for(double w : wa) {
                System.out.printf("%3f\t", w);
            }
            System.out.println();
        }
        System.out.println();
    }
}
