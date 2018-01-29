/**
 * Created by david on 2018/1/29.
 */
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
public class Outcast {
    private WordNet word;
   // private ST<String,Integer> dist;
    public Outcast(WordNet wordnet){
        // constructor takes a WordNet object
        if(wordnet == null) throw new IllegalArgumentException("illegal");
        word = wordnet;
       // dist = new ST<>();
    }

    public String outcast(String[] nouns) {
        // given an array of WordNet nouns, return an outcast
        if(nouns == null) throw new IllegalArgumentException("illegal");
        int max_value = Integer.MIN_VALUE;
        String out = nouns[0];
        for (String noun : nouns) {
            int dist = 0;
            for (String another : nouns) {
                if (!noun.equals(another)) {
                    dist += word.distance(noun, another);
                }
            }
            if (dist > max_value) {
                max_value = dist;
                out = noun;
            }
        }
        return out;

    }
    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}
