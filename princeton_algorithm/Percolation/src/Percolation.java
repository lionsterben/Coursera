/**
 * Created by david on 2017/10/21.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int [][] grid;
    private WeightedQuickUnionUF dav_1;
    private WeightedQuickUnionUF dav_2;
    private  int size;
    private int count = 0;
    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n<0");
        }
        size = n;
        grid = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                grid[i][j] = 0;
            }
        }
        //(0,0)start_node,n*n+1 (0,1)end_node
        //grid 1,n are index
        int squre = n*n;
        dav_1 = new WeightedQuickUnionUF(squre+2);
        dav_2 = new WeightedQuickUnionUF(squre+1);
        for (int col = 1; col <= n; col++) {
            dav_1.union(0, col);
            dav_1.union(squre+1,squre+1-col);
            dav_2.union(0,col);
        }
    }
    private int xyto1D(int row,int col){
        int temp = (row-1)*size+col;
        return temp;
    }
    private void check(int i){
        if(i<=0 || i>size) throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        check(row);
        check(col);
        if (grid[row-1][col-1]==1) return;
        grid[row-1][col-1] = 1;
        count += 1;
        int ind = xyto1D(row,col);
        if (row>1 && isOpen(row-1,col)){
                dav_1.union(ind,xyto1D(row-1,col));
                dav_2.union(ind,xyto1D(row-1,col));
        }
        if (row<size && isOpen(row+1,col)){
                dav_1.union(ind,xyto1D(row+1,col));
                dav_2.union(ind,xyto1D(row+1,col));
        }
        if (col>1 && isOpen(row,col-1)){
            dav_1.union(ind,xyto1D(row,col-1));
            dav_2.union(ind,xyto1D(row,col-1));
        }
        if (col<size && isOpen(row,col+1)){
            dav_1.union(ind,xyto1D(row,col+1));
            dav_2.union(ind,xyto1D(row,col+1));
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        check(row);
        check(col);
        if (grid[row-1][col-1]==1){
            return true;
        }
        else return false;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        return dav_2.connected(0,xyto1D(row, col))&&isOpen(row,col);
    }
    public     int numberOfOpenSites()       // number of open sites
    {
        return count;
    }
    public boolean percolates()              // does the system percolate?
    {
        if (size==1) return isOpen(1,1);
        return dav_1.connected(0,size*size+1);
    }

    public static void main(String[] args)   // test client (optional)
    {
        Percolation test = new Percolation(3);
        test.open(1,1);
        test.open(2,1);
        test.open(3,2);
        test.open(2,2);
        System.out.println(test.percolates());
    }
}
