/**
 * Created by david on 2018/1/29.
 */
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    private Digraph graph;
    private In word_set;
    private In id_set;
    private ST<Integer,Queue<String>> id_name;
    private ST<Integer,String> id_str;
    private ST<String,Queue<Integer>> name_id;
    private SAP find;
    public WordNet(String synsets, String hypernyms){
        // constructor takes the name of the two input files
        if(synsets == null || hypernyms == null) throw new IllegalArgumentException("illegal");
        word_set = new In(synsets);
        id_set = new In(hypernyms);
        id_name = new ST<>();
        name_id = new ST<>();
        id_str = new ST<>();
        //graph = new Digraph()
        while(!word_set.isEmpty()){
            String temp = word_set.readLine();
            String[] parse = temp.split(",");
            Integer id = Integer.parseInt(parse[0]);
            id_name.put(id,new Queue<String>());
            id_str.put(id,parse[1]);
            String[] name = parse[1].split(" ");
            for (String one_name:name) {
                id_name.get(id).enqueue(one_name);
                if(!name_id.contains(one_name)) name_id.put(one_name,new Queue<Integer>());
                name_id.get(one_name).enqueue(id);
            }
        }
        graph = new Digraph(id_name.size());
        while(!id_set.isEmpty()){
            String temp = id_set.readLine();
            String[] parse = temp.split(",");
            int edge_start = Integer.parseInt(parse[0]);
            for(int i=1;i<parse.length;i++){
                int edge_end = Integer.parseInt(parse[i]);
                graph.addEdge(edge_start,edge_end);
            }
        }
        find = new SAP(graph);
    }

    public Iterable<String> nouns(){
        // returns all WordNet nouns
        return name_id.keys();
    }

    public boolean isNoun(String word){
        // is the word a WordNet noun?
        if(word == null) throw new IllegalArgumentException("illegal");
        return name_id.contains(word);
    }

    public int distance(String nounA, String nounB){
        // distance between nounA and nounB (defined below)
        if(nounA == null || nounB == null) throw new IllegalArgumentException("illegal");
        return find.length(name_id.get(nounA),name_id.get(nounB));
    }

    public String sap(String nounA, String nounB){
        // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
        // in a shortest ancestral path (defined below)
		if(nounA == null || nounB == null) throw new IllegalArgumentException("illegal");
        int id = find.ancestor(name_id.get(nounA),name_id.get(nounB));
        return id_str.get(id);
    }

    public static void main(String[] args){
    }

}
