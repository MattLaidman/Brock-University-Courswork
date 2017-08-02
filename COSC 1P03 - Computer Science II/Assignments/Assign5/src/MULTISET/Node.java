package MULTISET;
public class Node<E> { // symmetrically linked node list Node<E> class
    Node<E> prev;
    E item;
    Node<E> next;
    public Node (Node<E> p, E i, Node<E> n) {
        prev = p;
        item = i;
        next = n;
    }
}