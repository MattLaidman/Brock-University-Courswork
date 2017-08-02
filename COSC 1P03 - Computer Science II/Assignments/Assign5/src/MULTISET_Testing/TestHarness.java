package MULTISET_Testing;
import MULTISET.*;

/**
 * This program is a test harness for the MULTISET package.
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */
public class TestHarness {


    @SuppressWarnings("unchecked")
    public TestHarness() {
        MultiSet data = new MyBag(new KeyedChar[] {new KeyedChar('a'), new KeyedChar('c'), new KeyedChar('b'), new KeyedChar('d'), new KeyedChar('c')});
        MultiSet data2 = new MySet(new KeyedChar[] {new KeyedChar('b'), new KeyedChar('e'), new KeyedChar('f'), new KeyedChar('a'), new KeyedChar('f')});
        MultiSet data3 = new MySet(new KeyedInt[] {new KeyedInt(2), new KeyedInt(5), new KeyedInt(1), new KeyedInt(3)});
        MultiSet data4 = new MySet();
        MultiSet data5 = new MyBag();
        data.add(new KeyedChar('f'));
        data.add(new KeyedChar('e'));
        data.add(new KeyedChar('b'));
        data3.add(new KeyedInt(4));

        Iterator multiIterator = data.iterator();
        System.out.print("Data 1 (MyBag): ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println();
        multiIterator = data2.iterator();
        System.out.print("Data 2 (MySet): ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println();
        multiIterator = data3.iterator();
        System.out.print("Data 3 (MySet): ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println();
        multiIterator = data4.iterator();
        System.out.print("Data 4 (MySet): ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println();
        multiIterator = data5.iterator();
        System.out.print("Data 5 (MyBag): ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        System.out.print("Data 1 Cardinality: ");
        System.out.println(data.cardinality() + "\n");

        System.out.print("Data 1 Multiplicity of \'b\': ");
        System.out.println(data.multiplicity(new KeyedChar('b')) + "\n");

        multiIterator = data.union(data).iterator();
        System.out.print("Data 1 Union Data 1: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data3.union(data3).iterator();
        System.out.print("Data 3 Union Data 3: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data.union(data2).iterator();
        System.out.print("Data 1 Union Data 2: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data2.union(data).iterator();
        System.out.print("Data 2 Union Data 1: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data.intersection(data2).iterator();
        System.out.print("Data 1 Intersection Data 2: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data2.intersection(data).iterator();
        System.out.print("Data 2 Intersection Data 1: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        multiIterator = data3.intersection(data3).iterator();
        System.out.print("Data 3 Intersection Data 3: ");
        while (multiIterator.hasNext()) {
            Keyed value = multiIterator.next();
            System.out.print(value.getKey());
        }
        System.out.println("\n");

        System.out.print("Data 2 Equal Data 1: " + data2.equal(data));
        System.out.println("\n");

        System.out.print("Data 2 Equal Data 2: " + data2.equal(data2));
        System.out.println("\n");

        System.out.print("Data 3 Is Empty: " + data3.isEmpty());
        System.out.println("\n");

        System.out.print("Data 4 Is Empty: " + data4.isEmpty());
        System.out.println("\n");

        System.out.print("Data 5 Is Empty: " + data5.isEmpty());
        System.out.println("\n");
    }


    public static void main(String[] args) {
        new TestHarness();
    }
}