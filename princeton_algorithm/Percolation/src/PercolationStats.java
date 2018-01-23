import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;
public class PercolationStats {
    private Percolation[] res;
    private double[] result;
    private int size;
    private void check(int i){
        if(i<=0 ) throw new IllegalArgumentException();
    }
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        check(n);
        check(trials);
        size = trials;
        result = new double[trials];
        res = new Percolation[trials];
        for(int i=0;i<trials;i++){
            res[i] = new Percolation(n);
        }
        //int probability = StdRandom.uniform(0,1);
        for(int i=0;i<trials;i++){
            while(!res[i].percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                res[i].open(row,col);
            }
            result[i] = (double) res[i].numberOfOpenSites()/(n*n);
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(result);

    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(result);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96*stddev()/Math.sqrt(size);

    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96*stddev()/Math.sqrt(size);
    }

    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats a = new PercolationStats(200,100);
        System.out.println(a.mean());
        System.out.println(a.stddev());
        System.out.println(a.confidenceLo());
        System.out.println(a.confidenceHi());

    }
}