/**
 * Created by david on 2017/12/3.
 */
import edu.princeton.cs.algs4.Stack;
public class Board {
    private int N;
    private int[][] board;
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        board = new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                board[i][j] = blocks[i][j];
            }
        }
    }
    public int dimension()  {
        // board dimension n
        return N;
    }
    public int hamming(){
        // number of blocks out of place
        int mistake = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(board[i][j]!=0 && board[i][j]!=N*i+j+1){
                    mistake++;
                }
            }
        }
        return mistake;
    }
    public int manhattan(){
        // sum of Manhattan distances between blocks and goal
        int mistake = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(board[i][j]!=0 && board[i][j]!=N*i+j+1){
                    int row = (board[i][j]-1)/N;
                    int col = board[i][j]-1-N*row;
                    mistake += java.lang.Math.abs(row-i)+java.lang.Math.abs(col-j);
                }
            }
        }
        return mistake;
    }
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    if (board[i][j] != 0) {
                        return false;
                    }
                }
                else {
                    if (board[i][j] != i * N + j + 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] temp_block = new int[N][N];
        int flag = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                temp_block[i][j] = board[i][j];
            }
        }
        for(int i=0;i<N-1;i++){
            for(int j=0;j<N;j++){
                if(temp_block[i][j]!=0 && temp_block[i+1][j]!=0){
                    swap(temp_block,i,j,i+1,j);
                    flag = 1;
                    break;
                }
            }
            if(flag==1) break;
        }
        Board twin_board = new Board(temp_block);
        return twin_board;
    }
    public boolean equals(Object y){
        // does this board equal y?
        if(y == this) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(that.dimension()!=this.dimension()) return false;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(that.board[i][j]!=this.board[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public Iterable<Board> neighbors(){
        // all neighboring boards
        Stack<Board> neighbor = new Stack<>();
        int[][] temp_block = new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                temp_block[i][j] = board[i][j];
            }
        }
        int row_zero = -1;
        int col_zero = -1;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(board[i][j] == 0){
                    row_zero = i;
                    col_zero = j;
                }
            }
        }
        assert (row_zero>=0);
        assert (col_zero>=0);
        if(row_zero == 0){
            if(col_zero == 0){
                swap(temp_block,row_zero,col_zero,row_zero,col_zero+1);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero,col_zero+1);//交换回去
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
            }
            else if(col_zero == N-1){
                swap(temp_block,row_zero,col_zero,row_zero,col_zero-1);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero,col_zero-1);//交换回去
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
            }
            else{
                swap(temp_block,row_zero,col_zero,row_zero,col_zero+1);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero,col_zero+1);//交换回去
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero+1,col_zero);
                swap(temp_block,row_zero,col_zero,row_zero,col_zero-1);
                neighbor.push(new Board(temp_block));
                swap(temp_block,row_zero,col_zero,row_zero,col_zero-1);//交换回去
            }
        }
        else if(row_zero == N-1) {
            if (col_zero == 0) {
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
            } else if (col_zero == N - 1) {
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
            } else {
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);//交换回去
            }
        }
        else{
            if(col_zero == 0) {
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero );//交换回去
            }
            else if(col_zero == N-1){
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero );
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero );//交换回去
            }
            else{
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero + 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero - 1, col_zero);
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero, col_zero - 1);//交换回去
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero );
                neighbor.push(new Board(temp_block));
                swap(temp_block, row_zero, col_zero, row_zero+1, col_zero );//交换回去
            }
        }
        return neighbor;
    }
    private void swap(int[][] board,int m_x,int m_y,int n_x,int n_y){
        int temp = board[m_x][m_y];
        board[m_x][m_y] = board[n_x][n_y];
        board[n_x][n_y] = temp;
    }
    public String toString(){
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args){
        // unit tests (not graded)
        int[][] block = new int[3][3];
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                block[i][j] = i*3+j+1;
            }
        }
        block[2][2] = 0;
        block[0][1] = 0;
        block[2][2] = 2;
        Board test = new Board(block);
        //System.out.println(test.dimension());
        //System.out.println(test.isGoal());
        //System.out.println(test.hamming());
        //System.out.println(test.neighbors());
        System.out.println(test.twin());
    }
}
