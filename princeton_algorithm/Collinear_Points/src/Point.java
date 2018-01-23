/**
 * Created by david on 2017/11/19.
 */
import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;
public class Point implements Comparable<Point>{
    private final int x;
    private final int y;
    public Point(int x, int y){
        // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }
    public   void draw(){
        // draws this point
        StdDraw.point(x,y);
    }
    public   void drawTo(Point that){
        // draws the line segment from this point to that point
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public String toString(){
        // string representation
        return "(" + x + ", " + y + ")";
    }
    public int compareTo(Point that){
        // compare two points by y-coordinates, breaking ties by x-coordinates
        if(this.y < that.y) return -1;
        if(this.y == that.y && this.x < that.x) return -1;
        if(this.y == that.y && this.x == that.x) return 0;
        return +1;
    }
    public double slopeTo(Point that){
        // the slope between this point and that point
        if(this.x == that.x && this.y == that.y){
            return Double.NEGATIVE_INFINITY;
        }
        if(this.x == that.x){
            return Double.POSITIVE_INFINITY;
        }
        if(this.y == that.y){
            return +0.0;
        }
        double c = (double) (that.y - this.y)/(that.x - this.x);
        return c;
    }
    public Comparator<Point> slopeOrder(){
        return  new BySlope();
    }
    private class BySlope implements Comparator<Point>{
        public int compare(Point m,Point n){
            double slope_1 = slopeTo(m);
            double slope_2 = slopeTo(n);
            if(slope_1 < slope_2) return -1;
            if(slope_1 == slope_2) return 0;
            return +1;
        }

    }
}
