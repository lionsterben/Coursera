//import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
/**
 * Created by david on 2017/11/10.
 */
public class Deque<Item> implements Iterable<Item> {
    //双向链表
    private int n;
    private Node first;
    private Node last;

    private class Node{
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {
        // construct an empty deque
        first = null;
        last = null;
        n = 0;

    }
    public boolean isEmpty(){
        // is the deque empty?
        return n == 0;
    }

    public int size(){
        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item){
        // add the item to the front
        //往a里加
        if(item == null) throw new java.lang.IllegalArgumentException();
        Node temp = new Node();
        temp.item = item;
        temp.next = null;
        temp.prev = null;
        if(isEmpty()) last = temp;
        else{
            first.prev = temp;
        }
        temp.next = first;
        first = temp;
        n++;
    }

    public void addLast(Item item){
        // add the item to the end
        if(item == null) throw new java.lang.IllegalArgumentException();
        Node temp = new Node();
        temp.item = item;
        temp.next = null;
        temp.prev = null;
        if(isEmpty()) first = temp;
        else{
            last.next = temp;
        }
        temp.prev = last;
        last = temp;
        n++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
        if(isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if(first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }
        n--;
        return item;
    }
    public Item removeLast(){
        // remove and return the item from the end
        if(isEmpty()) throw new java.util.NoSuchElementException("Stack overflow");
        Item item = last.item;
        last = last.prev;
        if(last != null) {
            last.next = null;
        }
        else {
            first = null;
        }
        n--;
        return item;
    }
    public Iterator<Item> iterator(){
        // return an iterator over items in order from front to end
        return new DequeueIterator();
    }
    private class DequeueIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return current != null;
         }
         public void remove(){
            throw new UnsupportedOperationException();
         }
        public Item next(){
            if(!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;

        }
    }
    public static void main(String[] args){
        // unit testing (optional)
        Deque<Integer> temp = new Deque<Integer>();
       // temp.addFirst(1);
        //a.addFirst(2);
        //a.addFirst(3);
       // temp.addLast(4);
       // temp.addLast(5);
        //StdIn(a.removeFirst());
        //System.out.println(temp.removeLast());

       // System.out.println(temp.removeFirst());
       // System.out.println(temp.removeLast());
        //System.out.println(temp.removeFirst());
        for(int i =0;i<10;i++){
            temp.addFirst(i);
        }
        for(int i=0;i<11;i++){
            System.out.println(temp.removeLast());
        }
        //System.out.println(a.removeFirst());
        //System.out.println(a.test());
    }
}
