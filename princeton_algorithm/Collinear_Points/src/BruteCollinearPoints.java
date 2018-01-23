import java.util.*;

/**
 * Created by david on 2017/11/19.
 */
public class BruteCollinearPoints {
    private List<Point[]> a = new ArrayList<>();
    private LineSegment[] b;
    public BruteCollinearPoints(Point[] points){
        // finds all line segments containing 4 points
        if(points == null) throw new java.lang.IllegalArgumentException();
        for(int i = 0;i<points.length;i++){
            if(points[i] == null) throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for(int i = 0;i<points.length-1;i++){
            if(points[i] == points[i+1]){
                throw new IllegalArgumentException();
            }
        }
        int n = points.length;
        for(int i = 0;i < n;i++ ){
            for(int j = i+1;j < n;j++){
                for(int p = j+1;p < n;p++){
                    for(int q = p+1;q < n;q++){
                        Point[] temp = new Point[4];
                        temp[0] = points[i];
                        temp[1] = points[j];
                        temp[2] = points[p];
                        temp[3] = points[q];
                        Arrays.sort(temp);
                        double slope_0 = temp[0].slopeTo(temp[1]);
                        double slope_1 = temp[1].slopeTo(temp[2]);
                        if(slope_0 != slope_1) break;;
                        double slope_2 = temp[2].slopeTo(temp[3]);
                        if(slope_0 == slope_1 && slope_1 == slope_2) a.add(temp);
                    }
                }
            }
        }
    }
    public LineSegment[] segments(){
        // the line segments
        int N = a.size();
        LineSegment[] res = new LineSegment[N];
        int count = 0;
        for(Point[] tmp:a){
            res[count] = new LineSegment(tmp[0],tmp[3]);
            count++;
        }
        return res;
    }
    public int numberOfSegments(){
        // the number of line segments
        return segments().length;
    }
}
