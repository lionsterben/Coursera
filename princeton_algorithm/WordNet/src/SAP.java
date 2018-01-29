/**
 * Created by david on 2018/1/29.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdOut;
public class SAP {
    private Digraph graph;
    public SAP(Digraph G){
        // constructor takes a digraph (not necessarily a DAG)
        if(G == null) throw new IllegalArgumentException("no digraph");
        graph = new Digraph(G);
    }
    private int length(BreadthFirstDirectedPaths path_v,BreadthFirstDirectedPaths path_w){
        int min_path = Integer.MAX_VALUE;
        int flag = 0;
        for(int i = 0;i<graph.V();i++){
            if(path_v.hasPathTo(i) && path_w.hasPathTo(i)){
                flag = 1;
                if(path_v.distTo(i)+path_w.distTo(i)<min_path){
                    min_path = path_v.distTo(i)+path_w.distTo(i);
                }
            }
        }
        if(flag == 0) return -1;
        return min_path;
    }

    private int ancestor(BreadthFirstDirectedPaths path_v,BreadthFirstDirectedPaths path_w){
        int min_path = Integer.MAX_VALUE;
        int ancestor = -1;
        int flag = 0;
        for(int i = 0;i<graph.V();i++){
            if(path_v.hasPathTo(i) && path_w.hasPathTo(i)){
                flag = 1;
                if(path_v.distTo(i)+path_w.distTo(i)<min_path){
                    min_path = path_v.distTo(i)+path_w.distTo(i);
                    ancestor = i;
                }
            }
        }
        if(flag == 0) return -1;
        return ancestor;
    }
    public int length(int v, int w){
        // length of shortest ancestral path between v and w; -1 if no such path
        if(v<0||v>graph.V()-1||w<0||w>graph.V()-1) throw new IllegalArgumentException("illegal argument");
        BreadthFirstDirectedPaths path_v = new BreadthFirstDirectedPaths(graph,v);
        BreadthFirstDirectedPaths path_w = new BreadthFirstDirectedPaths(graph,w);
        return length(path_v,path_w);
    }

    public int ancestor(int v, int w){
        // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
        if(v<0||v>graph.V()-1||w<0||w>graph.V()-1) throw new IllegalArgumentException("illegal argument");
        BreadthFirstDirectedPaths path_v = new BreadthFirstDirectedPaths(graph,v);
        BreadthFirstDirectedPaths path_w = new BreadthFirstDirectedPaths(graph,w);
        return ancestor(path_v,path_w);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
        if(v == null || w == null) throw new IllegalArgumentException("no argument");
        BreadthFirstDirectedPaths path_v = new BreadthFirstDirectedPaths(graph,v);
        BreadthFirstDirectedPaths path_w = new BreadthFirstDirectedPaths(graph,w);
        return length(path_v,path_w);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        // a common ancestor that participates in shortest ancestral path; -1 if no such path
        if(v == null || w == null) throw new IllegalArgumentException("no argument");
        BreadthFirstDirectedPaths path_v = new BreadthFirstDirectedPaths(graph,v);
        BreadthFirstDirectedPaths path_w = new BreadthFirstDirectedPaths(graph,w);
        return ancestor(path_v,path_w);
    }

    public static void main(String[] args){
        Digraph G = new Digraph(13);
        G.addEdge(7,3);
        G.addEdge(8,3);
        G.addEdge(3,1);
        G.addEdge(4,1);
        G.addEdge(5,1);
        G.addEdge(9,5);
        G.addEdge(10,5);
        G.addEdge(11,10);
        G.addEdge(12,10);
        G.addEdge(1,0);
        G.addEdge(2,0);
        SAP sap = new SAP(G);
        System.out.println(sap.length(1,6));
        System.out.println(sap.ancestor(1,6));
    }

}
