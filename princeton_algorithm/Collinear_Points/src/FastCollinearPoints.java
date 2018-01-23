import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
/**
 * Created by david on 2017/11/19.
 */
public class FastCollinearPoints {
    private  int linenum;
    private Node last;
    private class Node{
        private LineSegment value;
        private Node prev;
    }
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new java.lang.IllegalArgumentException();
        int num = points.length;
        Point[] clone = new Point[num];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            clone[i] = points[i];
        }
        Arrays.sort(clone);
        for (int i = 0; i < num - 1; i++) {
            if (clone[i] == clone[i + 1]) {
                throw new IllegalArgumentException();
            }
        }
        if (num < 4) return;
        for (int i = 0; i < num - 1; i++) {
            //对每个点分别求,
            int temp_num = 0;
            Point[] tempPoints = new Point[num - 1];
            for (int j = 0; j < num; j++) {
                if(i!=j) tempPoints[temp_num++] = clone[j];
            }

            Arrays.sort(tempPoints, clone[i].slopeOrder());
            int count = 0;
            Point min = null;
            Point max = null;
            for (int j = 0; j < (temp_num - 1); j++) {
                if (clone[i].slopeTo(tempPoints[j]) == clone[i].slopeTo(tempPoints[j + 1])) {
                    if (min == null) {
                        if (clone[i].compareTo(tempPoints[j]) > 0) {
                            max = clone[i];
                            min = tempPoints[j];
                        } else {
                            min = clone[i];
                            max = tempPoints[j];
                        }
                    }
                    if (min.compareTo(tempPoints[j + 1]) > 0) {
                        min = tempPoints[j + 1];
                    }
                    if (max.compareTo(tempPoints[j + 1]) < 0) {
                        max = tempPoints[j + 1];
                    }
                    count++;
                    if (j == (temp_num - 2)) {
                        if (count >= 2 && clone[i].compareTo(min) == 0) {//我是最小的一个时再加入,防止重复
                            addLine(min, max);
                        }
                        count = 0;
                        min = null;
                        max = null;
                    }
                } else {
                    if (count >= 2 && clone[i].compareTo(min) == 0) {
                        addLine(min, max);
                    }
                    count = 0;
                    min = null;
                    max = null;

                }
            }
        }
    }

    private void addLine(Point a,Point b){
        if(last != null){
            Node newNode = new Node();
            newNode.prev = last;
            newNode.value = new LineSegment(a,b);
            last = newNode;
        }
        else{
            last = new Node();
            last.value = new LineSegment(a,b);
        }
        linenum++;
        }

    public LineSegment[] segments(){
        // the line segments
        LineSegment[] res = new LineSegment[linenum];
        Node cur = last;
        for(int i = 0;i<linenum;i++){
            res[i] = cur.value;
            cur = cur.prev;}
            return res;
    }
    public int numberOfSegments(){
        // the number of line segments
        return linenum;
    }

}


