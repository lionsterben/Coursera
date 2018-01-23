/**
 * Created by david on 2017/11/10.
 */
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;
    public RandomizedQueue(){
        // construct an empty randomized queue
        //用一个数组来表示
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return n == 0;
    }
    public int size(){
        // return the number of items on the randomized queue
        return n;
    }
    private void resize(int capacity){
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for(int i = 0;i < n;i++){
            temp[i] = a[i];
        }
        a = temp;
    }
    public void enqueue(Item item){
        // add the item
        if(item == null) throw new java.lang.IllegalArgumentException("fuck");
        if(n == a.length) resize(2*a.length);
        a[n++] = item;
    }
    public Item dequeue(){
        // remove and return a random item
        //随机选择一个pop，将开始一个元素放置在之前随机选择的位置上
        if(isEmpty()) throw new java.util.NoSuchElementException();
        int ch = StdRandom.uniform(n);
        Item tem = a[ch];
        a[ch] = a[n-1];
        a[n-1] = null;
        n--;
        if(n > 0 && n == a.length/4) resize(a.length/2);
        return tem;
    }
    public Item sample(){
        // return a random item (but do not remove it)
        if(isEmpty()) throw new java.util.NoSuchElementException();
        int ch = StdRandom.uniform(n);
        return a[ch];
    }
    public Iterator<Item> iterator(){
        // return an independent iterator over items in random order
        return new RandomQueueIterator();
    }
    private class RandomQueueIterator implements Iterator<Item>{
        private int i;
        public RandomQueueIterator(){
            i = n-1;
            StdRandom.shuffle(a);
        }
        public boolean hasNext(){
            return i >= 0;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }
    public static void main(String[] args){
        // unit testing (optional)
        RandomizedQueue<Integer> temp = new RandomizedQueue<Integer>();
        for(int i=0;i<100;i++){
            temp.enqueue(i);
        }
        for(int i=0;i<100;i++){
            //System.out.println(temp.dequeue());
            System.out.println(temp.size());
        }
        System.out.println(temp.isEmpty());
}
}
