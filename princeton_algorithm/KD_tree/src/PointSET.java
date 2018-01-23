/**
 * Created by david on 2018/1/11.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> res;

    public PointSET(){
       res = new SET<Point2D>();
    }

    public boolean isEmpty(){
        return res.isEmpty();
    }

    public int size(){
        return res.size();
    }

    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException("null argument");
        if(!res.contains(p)){
            res.add(p);
        }
    }

    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException("null argument");
        return res.contains(p);
    }

    public void draw(){
        if(!res.isEmpty()){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            for(Point2D point:res){
                StdDraw.point(point.x(),point.y());
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        Queue<Point2D> include  = new Queue<>();
        for(Point2D p:res){
            if(rect.contains(p)){
                include.enqueue(p);
            }
        }
        return include;
    }

    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException("null argument");
        if(res.isEmpty()) return null;
        Point2D nearest_point = res.min();
        for(Point2D point:res){
            if(point.distanceSquaredTo(p) < nearest_point.distanceSquaredTo(p)){
                nearest_point = point;
            }
        }
        return nearest_point;
    }

    public static void main(String[] args){
        PointSET temp = new PointSET();
        Point2D p = new Point2D(0.5,0.6);
        temp.insert(p);
        temp.draw();
        System.out.println(temp.isEmpty());
    }

}



