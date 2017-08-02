import java.util.Vector;
import java.util.Stack;

public class Hanoi {
    final int size=3;//don't change this!

    public Hanoi() {
        Vector<Stack<Integer>> pegs=new Vector<Stack<Integer>>();

        //Note: This loop is *always* size 3, irrespective of the number of discs!
        for (int i=0;i<3;i++) pegs.add(new Stack<Integer>());

        //This loads the 'from' peg up with 'size' numbers
        for (int i=0;i<size;i++) pegs.get(0).push(i+1);
        displayState(pegs);

        //Let's solve here, by having it move from peg 0 to peg 2
        solve(0,2,size,pegs);
        //Done solving? Great!
        System.out.println("Done!");
        displayState(pegs);
    }


    private void solve(int from, int to, int qty, Vector<Stack<Integer>> state) {
        System.out.println("Moving " + qty + " from " + from + " to " + to + ".");
        if (qty == 1) {
            state.elementAt(to).push(state.elementAt(from).pop());
        } else {
            solve(from, 3-to-from, qty-1, state);
            solve(from, to, 1, state);
            solve(3-to-from, to, qty-1, state);
        }
    }


    private void displayState(Vector<Stack<Integer>> state) {
        System.out.println("---");
        for (Stack<Integer> peg:state) {
            for (int i:peg) System.out.print("\t"+i);
            System.out.println();
        }
        System.out.println("---");
    }

    public static void main(String[] args) {new Hanoi();}
}
