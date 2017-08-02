/**
 * This program...
 *
 * @author Matt Laidman
 * @version 1.0 (February 2014)
 */
public class PartTwo {


    public PartTwo() {
        useNum("-1234567");
        useNum("654323");
        useNum("-765432222256");
    }


    private void useNum (String num) {

        boolean flag = true;

        System.out.println("Data:\t\t"+num);

        if (num.charAt(0) == '-') {
            flag = false;
            num = num.substring(1, num.length());
        }

        System.out.print("Digits:\t\t");
        for (int i = 0 ; i < num.length() ; i++) {
            System.out.print((num.charAt(i)-48)/2 + "\t\t");
        }
        System.out.println();
        System.out.print("Sign:\t\t");
        if (!flag) {
            System.out.println("Negative\n");
        } else {
            System.out.println("Positive\n");
        }
    }


    public static void main(String[] args) {
        new PartTwo(); //this is 'harness' for assign
    }
}