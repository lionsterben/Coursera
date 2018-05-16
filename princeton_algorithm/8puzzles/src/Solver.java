/**
 * Created by david on 2017/12/3.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.SET;

//cache,不要重复计算距离
//开始和isslove重复

public class Solver {
    private Stack<Board> trace = new Stack<>();
    private Board start;
    private int final_move;
    private int slove = -1;
    private int run = -1;

    private class SearchNode implements Comparable<SearchNode> {
        private int manhattan;
        private int moves;
        private int priority;
        private int N;
        private Board now;
        private SearchNode previous;

        public SearchNode(Board temp, SearchNode before, int move) {
            now = temp;
            N = now.dimension();
            moves = move + 1;
            manhattan = now.manhattan();
            priority = moves + manhattan;
            previous = before;
        }


        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }

        public Board get_board() {
            return now;
        }

        public int get_move() {
            return moves;
        }

        public SearchNode get_prevnode() {
            return previous;
        }

        ;
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new java.lang.IllegalArgumentException();
        start = initial;
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        run = 0;
        Board twin = start.twin();
        MinPQ<SearchNode> younger = new MinPQ<>();
        MinPQ<SearchNode> elder = new MinPQ<>();
        younger.insert(new SearchNode(start, null, -1));
        elder.insert(new SearchNode(twin, null, -1));
        while (true) {
            SearchNode min_1 = younger.delMin();
            SearchNode min_2 = elder.delMin();
            if (min_1.get_board().isGoal()) {
                slove = 0;
                final_move = min_1.get_move();
                SearchNode final_node = min_1;
                while (!final_node.get_board().equals(start)) {
                    trace.push(final_node.get_board());
                    final_node = final_node.get_prevnode();
                }
                trace.push(start);
                return true;
            }
            if (min_2.get_board().isGoal()) return false;
            if (min_1.get_move() == 0) {
                for (Board s : min_1.get_board().neighbors()) {
                    younger.insert(new SearchNode(s, min_1, min_1.get_move()));
                }
            } else {
                for (Board s : min_1.get_board().neighbors()) {
                    if (!s.equals(min_1.get_prevnode().get_board())) {
                        younger.insert(new SearchNode(s, min_1, min_1.get_move()));
                    }
                }
            }
            if (min_2.get_move() == 0) {
                for (Board s : min_2.get_board().neighbors()) {
                    elder.insert(new SearchNode(s, min_2, min_2.get_move()));

                }
            } else if (min_2.get_move() != 0) {
                for (Board s : min_2.get_board().neighbors()) {
                    if (!s.equals(min_2.get_prevnode().get_board())) {
                        elder.insert(new SearchNode(s, min_2, min_2.get_move()));
                    }
                }
            }


        }

    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (run == 0) {
            if (slove != -1) return final_move;
            else return -1;
        } else {
            boolean a = this.isSolvable();
            run = 0;
            if (slove != -1) return final_move;
            else return -1;
        }
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (run == 0) {
            if (slove != -1) return trace;
            else return null;
        } else {
            boolean a = this.isSolvable();
            run = 0;
            if (slove != -1) return trace;
            else return null;
        }
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        int[][] blocks = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                blocks[i][j] = i * 3 + j + 1;
        blocks[2][2] = 0;
        blocks[0][1] = 0;
        blocks[2][2] = 2;
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
