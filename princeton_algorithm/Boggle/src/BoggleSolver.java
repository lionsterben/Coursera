
import edu.princeton.cs.algs4.SET;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TrieSET dict;
    private SET<String> res;
    private BoggleBoard board;
    //private boolean[][] marked;
    public BoggleSolver(String[] dictionary){
        if(dictionary == null) throw  new IllegalArgumentException("null");
        this.dict = new TrieSET();
        for(String str:dictionary) dict.add(str);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        if(board == null) throw  new IllegalArgumentException("null");
        this.res = new SET<>();;
        this.board = board;
        boolean[][] marked;
        for(int i = 0; i < board.rows(); i++){
            for(int j = 0; j < board.cols(); j++){
                marked = new boolean[board.rows()][board.cols()];
                for(int m = 0; m < board.rows(); m++) {
                    for (int n = 0; n < board.cols(); n++) {
                        marked[m][n] = false;
                    }
                }
                dfsBoard(i, j, new StringBuilder(change_Q(board.getLetter(i,j))), marked);
            }
        }
        return res;
    }
    private boolean[][] mark_clone(boolean[][] mark){
         int m = mark.length;
         int n = mark[0].length;
         boolean[][] res = new boolean[m][n];
         for(int i = 0; i < m;i++){
             for(int j = 0;j < n;j++){
                 res[i][j] = mark[i][j];
             }
         }
         return res;
    }
    private String change_Q(char tmp){
        if(tmp == 'Q'){
            return "QU";
        }
        else return String.valueOf(tmp);
    }
    private void dfsBoard(int row, int col, StringBuilder pre, boolean[][] marked){//new StringBuilder(pre):现在的word
        if(!dict.contain_prefix(pre.toString())) return;
        if(new StringBuilder(pre).length() > 2 && dict.contains(pre.toString())) res.add(new String(pre));
        marked[row][col] = true;
        //System.out.println("haha");
        //System.out.println(row);
        //System.out.println(col);
        //System.out.println(new StringBuilder(pre));
        //System.out.println(marked[0][3]);
        if(board.rows() == 1 && board.cols() == 1) return;
        else if(board.rows() == 1){
            if(col == 0) {if(!marked[row][col+1])  dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));}
            else if(col == board.cols() - 1) {if(!marked[row][col-1]) dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));}
            else{
                 if(!marked[row][col+1]) dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                 if(!marked[row][col-1]) dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
            }
        }
        else if(board.cols() == 1) {
            if (row == 0){ if(!marked[row + 1][col]) dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));}
            else if (row == board.rows() - 1){if(!marked[row - 1][col]) dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));}
            else {
                if (!marked[row - 1][col])
                    dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                if (!marked[row + 1][col])
                    dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));


            }
        }
        else {

            if (row == 0) {
                if (col == 0) {
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row][col + 1])
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                    if (!marked[row + 1][col + 1])
                        dfsBoard(row + 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col + 1))), mark_clone(marked));
                } else if (col == board.cols() - 1) {
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row + 1][col - 1])
                        dfsBoard(row + 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col - 1))), mark_clone(marked));
                } else {
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row][col + 1]) {
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));

                    }
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row + 1][col + 1])
                        dfsBoard(row + 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col + 1))), mark_clone(marked));
                    if (!marked[row + 1][col - 1])
                        dfsBoard(row + 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col - 1))), mark_clone(marked));
                }
            } else if (row == board.rows() - 1) {
                if (col == 0) {
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row][col + 1])
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                    if (!marked[row - 1][col + 1])
                        dfsBoard(row - 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col + 1))), mark_clone(marked));
                } else if (col == board.cols() - 1) {
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row - 1][col - 1])
                        dfsBoard(row - 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col - 1))), mark_clone(marked));
                } else {
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row][col + 1])
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row - 1][col + 1])
                        dfsBoard(row - 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col + 1))), mark_clone(marked));
                    if (!marked[row - 1][col - 1])
                        dfsBoard(row - 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col - 1))), mark_clone(marked));
                }
            } else {
                if (col != 0 && col != board.cols() - 1) {
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row][col + 1])
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                    if (!marked[row - 1][col - 1])
                        dfsBoard(row - 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col - 1))), mark_clone(marked));
                    if (!marked[row - 1][col + 1])
                        dfsBoard(row - 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col + 1))), mark_clone(marked));
                    if (!marked[row + 1][col - 1])
                        dfsBoard(row + 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col - 1))), mark_clone(marked));
                    if (!marked[row + 1][col + 1])
                        dfsBoard(row + 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col + 1))), mark_clone(marked));
                } else if (col == 0) {
                    if (!marked[row][col + 1])
                        dfsBoard(row, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col + 1))), mark_clone(marked));
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row - 1][col + 1])
                        dfsBoard(row - 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col + 1))), mark_clone(marked));
                    if (!marked[row + 1][col + 1])
                        dfsBoard(row + 1, col + 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col + 1))), mark_clone(marked));
                } else {
                    if (!marked[row][col - 1])
                        dfsBoard(row, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row, col - 1))), mark_clone(marked));
                    if (!marked[row - 1][col])
                        dfsBoard(row - 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col))), mark_clone(marked));
                    if (!marked[row + 1][col])
                        dfsBoard(row + 1, col, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col))), mark_clone(marked));
                    if (!marked[row - 1][col - 1])
                        dfsBoard(row - 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row - 1, col - 1))), mark_clone(marked));
                    if (!marked[row + 1][col - 1])
                        dfsBoard(row + 1, col - 1, new StringBuilder(pre).append(change_Q(board.getLetter(row + 1, col - 1))), mark_clone(marked));
                }
            }
        }

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if(word == null) throw  new IllegalArgumentException("null");
        if(dict.contains(word)){
            int length = word.length();
            if(length >= 0 && length <= 2) return 0;
            else if(length >= 3 && length <= 4) return 1;
            else if(length == 5) return 2;
            else if(length == 6) return 3;
            else if(length == 7) return 5;
            else return 11;
        }
        else return 0;
    }

    public static void main(String[] args){
        String[] a = new String[2];
        a[0] = "ATE";
        a[1] = "QUI";
        //System.out.println(ha.contain_new StringBuilder(pre)fix("TYF"));
        //System.out.println(ha.contains("TY"));
             char[][] shu = {
                     {'A', 'T', 'E', 'E'}

                     };




        BoggleSolver b = new BoggleSolver(a);
        BoggleBoard c = new BoggleBoard(shu);
        for(String i:b.getAllValidWords(c)){
            System.out.println(i);
        }











     }
    }
