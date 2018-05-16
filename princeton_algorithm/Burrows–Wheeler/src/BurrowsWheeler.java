/**
 * Created by f1412 on 2018/5/15.
 */
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;


public class BurrowsWheeler {
    //apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform(){
        String input = BinaryStdIn.readString();
        String doubleInput = input.concat(input);
        CircularSuffixArray tmp = new CircularSuffixArray(input);
        int ind0 = 0;
        int[] res = new int[input.length()];
        for(int i = 0; i < input.length(); i++){
            res[i] = tmp.index(i);
            if(res[i] == 0){
                ind0 = i;
            }
        }
        BinaryStdOut.write(ind0, 32);
        for(int i : res){
            BinaryStdOut.write((int)doubleInput.charAt(i+input.length()-1),8);
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform(){
        //apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
        int R = 256;
        int first = BinaryStdIn.readInt(32);
        Queue<String> tmp = new Queue<>();
        while (!BinaryStdIn.isEmpty()){
            tmp.enqueue(String.valueOf(BinaryStdIn.readChar()));
        }
        int N = tmp.size();
        String[] t = new String[N];
        String[] top = new String[N];
        int count_1 = 0;
        for(String i : tmp){
            t[count_1] = i;
            top[count_1] = i;
            count_1 += 1;
        }
        //System.out.println(count_1);
        String[] aux = new String[N];
        int[] count = new int[R+1];
        for (int i = 0; i < N; i++){
            count[top[i].charAt(0) + 1]++;
        }
        for (int r = 0; r < R; r++){
            count[r + 1] += count[r];
        }
        int[] next = new int[N];
        for (int i = 0; i < N; i++){
            int ind = count[top[i].charAt(0)];
            aux[count[top[i].charAt(0)]] = top[i];
            next[ind] = i;
            count[top[i].charAt(0)] += 1;
        }
        for (int i = 0; i < N; i++){
            top[i] = aux[i];
        }
        int print = first;
        StringBuilder fina = new StringBuilder();
        while (true){
            fina.append(top[print]);
            print = next[print];
            if(print == first && fina.length() == N){
                break;
            }
        }
        BinaryStdOut.write(fina.toString());
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }
        else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }


}
