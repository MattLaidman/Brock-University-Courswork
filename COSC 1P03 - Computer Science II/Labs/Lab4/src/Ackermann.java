/**
 * This program...
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */
public class Ackermann {

    private int counter;

    public Ackermann() {
        counter = 0;
        System.out.println("Testing on (0, 0): " + doFunction(0, 0) + " [" + counter + "]");
        counter = 0;
        System.out.println("Testing on (1, 0): " + doFunction(1, 0) + " [" + counter + "]");
        counter = 0;
        System.out.println("Testing on (0, 1): " + doFunction(0, 1) + " [" + counter + "]");
        counter = 0;
        System.out.println("Testing on (1, 3): " + doFunction(1, 3) + " [" + counter + "]");
        counter = 0;
        System.out.println("Testing on (2, 20): " + doFunction(2, 20) + " [" + counter + "]");
    }


    private int doFunction (int x, int y) {
        counter++;
        if (x == 0) {
            return y+1;
        }
        if (y == 0) {
           return doFunction(x-1, 1);
        }
        return doFunction(x-1, doFunction(x, y-1));
    }


    public static void main(String[] args) {
        new Ackermann();
    }
}