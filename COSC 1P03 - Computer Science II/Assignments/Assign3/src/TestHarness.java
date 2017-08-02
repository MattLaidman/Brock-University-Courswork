import BigNumbers.*;

/**
 * This program is a test harness written to demonstrate the BigNumbers package.
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */

public class TestHarness {


    public TestHarness() {
        BNum m = new ConBNum("-6532265");
        BNum n = new LinkBNum(54321);
        BNum o = m.clone();
        System.out.println();
        System.out.println("m: " + m.toString());
        System.out.println("n: " + n.toString());
        System.out.println("o: " + o.toString());
        System.out.println();
        System.out.println("sign of m: " + m.getSign());
        System.out.println();
        System.out.println("3rd digit of n: " + n.getDigit(3));
        System.out.println();
        System.out.println("m == n: " + m.equals(n));
        System.out.println();
        System.out.println("m == o: " + m.equals(o));
        System.out.println();
        System.out.println("m < n: " + m.lessThan(n));
        System.out.println();
        System.out.println("n < m: " + m.lessThan(o));
        System.out.println();
        System.out.println("m + n: " + m.add(n));
        System.out.println();
        System.out.println("m - n: " + m.sub(n));
        System.out.println();
        System.out.println("(m - n) + o: " + m.sub(n).add(o));
        System.out.println();
        m = new ConBNum("-777998843224");
        n = new LinkBNum(-556432563343L);
        System.out.println("m: " + m.toString());
        System.out.println("n: " + n.toString());
        System.out.println();
        System.out.println("m < n: " + m.lessThan(n));
        System.out.println();
        System.out.println("m + n: " + m.add(n));
        System.out.println();
        System.out.println("3rd digit of m: " + m.getDigit(3));
        System.out.println();
        m = new ConBNum("777998843224");
        n = new LinkBNum(556432563343L);
        System.out.println("m: " + m.toString());
        System.out.println("n: " + n.toString());
        System.out.println();
        System.out.println("m + n: " + n.sub(m));
    }


    public static void main(String[] args) {
        new TestHarness();
    }
}