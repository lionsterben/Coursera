/**
 * Created by david on 2018/1/11.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int N;

        private Node(Point2D p, RectHV rect, int N) {
            this.p = p;
            this.rect = rect;
            this.N = N;
        }
    }

    private Node root;

    public KdTree() {
        root = null;
    }



    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }
    private int size(Node x){
        if(x == null) return 0;
        else return x.N;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        RectHV rect = new RectHV(0, 0, 1, 1);
        root = insert(root, p, rect, 0);
    }

    private Node insert(Node x, Point2D p, RectHV rect, int flag) {//if flag==0 yong x;flag==1,yong y
            if (x == null) {
                //RectHV rect = new RectHV(0,0,1,1);
                return new Node(p, rect, 1);
            } else {
                if (!x.p.equals(p)) {
                    if (flag == 0) {
                        if (p.x() < x.p.x()) {
                            RectHV rec = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
                            x.lb = insert(x.lb, p, rec, 1);

                        } else {
                            RectHV rec = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
                            x.rt = insert(x.rt, p, rec, 1);
                        }
                    } else {
                        if (p.y() < x.p.y()) {
                            RectHV rec = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
                            x.lb = insert(x.lb, p, rec, 0);

                        } else {
                            RectHV rec = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
                            x.rt = insert(x.rt, p, rec, 0);
                        }
                    }
                }
            }

            x.N = size(x.lb) + size(x.rt) + 1;
            return x;
        }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int flag) {//flag=0 比较x
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        if (flag == 0) {
            if (p.x() < x.p.x()) return contains(x.lb, p, 1);
            else return contains(x.rt, p, 1);
        } else {
            if (p.y() < x.p.y()) return contains(x.lb, p, 0);
            else return contains(x.rt, p, 0);
        }

    }

    public void draw() {
        draw(root, 0);
    }

    private void draw(Node x, int flag) {//flag==0,red
        if (x != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(x.p.x(),x.p.y());
            if (flag == 0) {
                //StdDraw.setPenColor(StdDraw.BLACK);
                //StdDraw.setPenRadius(0.01);
                //StdDraw.point(x.p.x(),x.p.y());
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
                draw(x.lb, 1);
                draw(x.rt, 1);
            } else {
                //StdDraw.setPenColor(StdDraw.BLACK);
                //StdDraw.setPenRadius(0.01);
                //StdDraw.point(x.p.x(),x.p.y());
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
                draw(x.lb, 0);
                draw(x.rt, 0);
            }
        }
    }

    private Queue<Point2D> res;

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");
        res = new Queue<>();
        range(root, rect);
        return res;

    }

    private void range(Node x, RectHV rect) {
        if (x != null) {
            if (rect.contains(x.p)) {
                res.enqueue(x.p);
            }
            if (x.lb != null &&rect.intersects(x.lb.rect)) {
                range(x.lb, rect);
            }
            if (x.rt !=null && rect.intersects(x.rt.rect)) {
                range(x.rt, rect);
            }
        }
    }

    private Point2D min_point;

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        min_point = root.p;
        nearest(root, p);
        return min_point;
    }

    private void nearest(Node x, Point2D p) {
        if (x != null) {
            if (x.p.distanceSquaredTo(p) < min_point.distanceSquaredTo(p)) {
                min_point = x.p;
            }
            if(x.lb != null && min_point.distanceSquaredTo(p)>x.lb.rect.distanceSquaredTo(p)){
                nearest(x.lb,p);
            }
            if(x.rt != null && min_point.distanceSquaredTo(p)>x.rt.rect.distanceSquaredTo(p)){
                nearest(x.rt,p);
            }
        }
    }

    public static void main(String[] args) {
        KdTree res = new KdTree();
        res.insert(new Point2D(0.7,0.2));
        res.insert(new Point2D(0.5,0.4));
        res.insert(new Point2D(0.2,0.3));
        res.insert(new Point2D(0.4,0.7));
        res.insert(new Point2D(0.9,0.6));


        System.out.println(res.nearest(new Point2D(0,0)));
    }
}


