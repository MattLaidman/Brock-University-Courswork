package assign2;

/**
 * AStack class is the array implementation of a stack used in the iterative traversal of the
 * un-threaded Binary Search Tree
 *
 * ***************** Global Variables *****************
 *
 * top          - An integer storing the index of the 'top' node in the stack.
 * stack        - An array of TNodes to store the nodes in the tree as they are pushed to the stack.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 1, 2014)
 */

public class AStack {
    private int top;
    private TNode[] stack;

    /**
     * Default constructor to create a stack of size 100.
     */

    public AStack ( ) {
        this(100);
    }

    /**
     * Constructor to create a stack of a given size.
     * Stack overflow occurs if stack created with 0 or negative space.
     *
     * @param size      - The size to reserve for the stack.
     */

    public AStack(int size) {
        if (size <= 0) {
            throw new AStackException("Stack Overflow Exception");
        }
        stack = new TNode[size];
        top = -1;
    }

    /**
     * push function 'pushes' an item on to the top of the stack and increases the top integer.
     *
     * @param item      - The item to push on to the stack.
     */

    public void push(TNode item) {
        if (top == stack.length) {
            throw new AStackException("Stack Overflow Exception");
        }
        top++;
        stack[top] = item;
    }

    /**
     * pop function 'pops' an item from the top of the stack and decreases the top integer.
     * Stack underflow occurs if there is no item to pop.
     *
     * @return          - The item popped off the stack.
     */

    public TNode pop() {
        if (top == -1) {
            throw new AStackException("Stack Underflow Exception");
        }
        top--;
        return stack[top+1];
    }

    /**
     * isEmpty boolean function returns whether or not the stack is empty.
     *
     * @return True if stack is empty, false otherwise.
     */

    public boolean isEmpty() {
        return (top == -1);
    }
}
