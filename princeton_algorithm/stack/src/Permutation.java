/**
 * Created by david on 2017/11/10.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import java.util.stream.Stream;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> temp = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            String item = StdIn.readString();
            temp.enqueue(item);
        }
        int k = Integer.parseInt(args[0]);
        for(int i=0;i<k;i++){
            StdOut.println(temp.dequeue());
        }
    }
}
