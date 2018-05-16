/**
 * Created by f1412 on 2018/5/15.
 * @author david
 */

public class CircularSuffixArray {
    private int[] res;
    private String word;
    private final int R = 256;
    private String doubleInput;
    public CircularSuffixArray(String s){
        // circular suffix array of s
        if (s == null){
            throw new IllegalArgumentException("string is null");
        }
        this.word = s;
        doubleInput = word.concat(word);
        res = MSD();
    }

    private int charAt(int str, int d) {
        int len = word.length();
        assert d >= 0 && d <= len;
        if (d == len) {return -1;}
        return doubleInput.charAt(str + d);
    }

    private int[] MSD()
    {
        //利用下标表示后缀
        int n = word.length();
        int[] string = new int[n];
        int[] aux = new int[n];
        for(int i = 0; i < n; i++) {string[i] = i;}
        sort(string, 0, n-1, 0, aux);
        return string;
    }

    private void sort(int[] a, int lo, int hi, int d, int[] aux){
        if(hi <= lo) {return;}
        int[] count = new int[R+2];

        for (int i = lo; i <= hi; i++){
            int c = charAt(a[i], d);
            count[c+2]++;
        }

        for (int r = 0; r < R+1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }
    }
    public int length(){
        return word.length();
    }
    public int index(int i){
        if(i < 0 || i >= word.length()){
            throw new IllegalArgumentException("index out of range");
        }
        return res[i];
    }
    public static void main(String[] args){
        // unit testing (required)
        CircularSuffixArray tmp = new CircularSuffixArray("SLTBYZMACM");
        int[] b = tmp.MSD();
        for(int i:b){
            System.out.println(i);
        }

    }
}
