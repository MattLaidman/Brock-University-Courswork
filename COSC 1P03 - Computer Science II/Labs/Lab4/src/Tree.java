import Media.*;

public class Tree {
    TurtleDisplayer canvas;
    Turtle pen;

    final double angle=Math.PI/3;


    public Tree() {
        pen=new Turtle();
        canvas=new TurtleDisplayer(pen, 500, 500);
        pen.moveTo(0,-135);
        pen.setSpeed(0);
        pen.left(Math.PI/2);
        pen.penDown();
        branch(15,120);
        pen.penUp();
        canvas.close();
    }

    private void branch(int depth, double length) {
        if (depth == 0) {
            pen.forward(length);
            pen.backward(length);
        } else {
            pen.forward(length);
            pen.right(Math.PI/6);
            branch(depth-1, (2.0/3.0)*length);
            pen.left(Math.PI/6);
            pen.left(Math.PI/3);
            branch(depth-1, (2.0/3.0)*length);
            pen.right(Math.PI/3);
            pen.backward(length);
        }
    }

    public static void main(String[] args) {new Tree();}
}
