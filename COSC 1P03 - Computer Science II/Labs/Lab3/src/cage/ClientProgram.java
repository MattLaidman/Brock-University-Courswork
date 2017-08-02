package cage; //Note that this is a different package!
import wicker.*; //This means we need to 'import' the two classes we wish to borrow!

public class ClientProgram {
    public static void main(String[] args) {
        test("alderman");
        test("abba");
        test("Don't worry! I make sure this doesn't include the letter B! ... dang.");
        test("Due to proper recovery of exceptions, the program can continue!");
        System.out.println("\nTime to crash the program!");
        System.out.println(new BeeBasket("No! I wasn't being careful!").tally());
    }

    private static void test(String txt) {
        BeeBasket bb=new BeeBasket(txt);

        //Note: If you don't use a try/catch here, it'll crash, and the 'stack trace' will include the
        //	message!
        try {
            System.out.print("Tally on "+txt+":\t");
            System.out.println(bb.tally());
        }
        catch (MelissophobiaException me) {
            System.out.println("\nOh crud. There seems to be a problem: "+me.getMessage());
        }
        catch (Exception e) {
            System.out.println("There is NO way this should ever be thrown!");
        }
    }
}
