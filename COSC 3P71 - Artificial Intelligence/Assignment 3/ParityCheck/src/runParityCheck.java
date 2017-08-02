/**
 * COSC 3P71 - Assignment 3
 * November 30th, 2015
 *
 * Matt Laidman, ml12ef, 5199807
 * Edward Shin, es13pw, 5517065
 *
 * runParityCheck class is the class which is called from the command line.
 * The expected parameter format
 */

public class runParityCheck {

    public static void main (String[] args) {

        double learningRate;
        int hiddenNodes;
        int epochs;
        long seed;

        try {
            if (args.length != 4) {
                throw new Exception();
            }
            learningRate = Double.parseDouble(args[0]);
            hiddenNodes = Integer.parseInt(args[1]);
            epochs = Integer.parseInt(args[2]);
            seed = Integer.parseInt(args[3]);
        } catch (Exception e) {
            printExecHelp();
            return;
        }

        ParityCheck pc = new ParityCheck(learningRate, hiddenNodes, epochs, seed);
        pc.run();
    }

    private static void printExecHelp ( ) {

        System.out.println("\nParityCheck should be executed in the following format:\n");
        System.out.println("java -jar ParityCheck.jar learningRate hiddenNodes epochs seed\n");
        System.out.println("Where:\n\n\tlearningRate\tThe rate at which the network will learn. ([0.0, 1.0])");
        System.out.println("\thiddenNodes\t\tThe number of hidden nodes in the network. (Integer value)");
        System.out.println("\tepochs\t\t\tThe number of epochs which will take place during learning. (Integer value)");
        System.out.println("\tseed\t\t\tThe seed used in the RNG. (Long integer value)\n");
    }
}
