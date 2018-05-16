import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by f1412 on 2018/5/15.
 * @author david
 */
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int R = 256;
        int[] alpha = new int[R];
        for (int i = 0; i < R; i++) {
            alpha[i] = i;
        }
        String input = BinaryStdIn.readString();
        for (int i = 0; i < input.length(); i++) {
            char value = input.charAt(i);
            int tmp = alpha[(int)value];
            BinaryStdOut.write(tmp, 8);
            for (int j = 0; j < R; j++) {
                if (alpha[j] < tmp) {
                    alpha[j] = alpha[j] + 1;
                }
                alpha[value] = 0;
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        int R = 256;
        int[] alpha = new int[R];
        for (int i = 0; i < R; i++) {
            alpha[i] = i;
        }
        StringBuilder fina = new StringBuilder();
        while (!BinaryStdIn.isEmpty()){
            int tmp = BinaryStdIn.readInt(8);
            int value = -1;
            for (int i : alpha){
                if(alpha[i] == tmp){
                    value = i;
                }
            }
            assert value != -1;
            fina.append((char)value);

            //BinaryStdOut.write((char)value, 8);
            for (int j = 0; j < R; j++) {
                if (alpha[j] < tmp) {
                    alpha[j] = alpha[j] + 1;
                }
                alpha[value] = 0;
            }

        }
        BinaryStdOut.write(fina.toString());
        BinaryStdOut.close();

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args){
        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();;
        }
        else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}

