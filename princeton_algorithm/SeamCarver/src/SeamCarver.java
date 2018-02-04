/**
 * Created by david on 2018/2/3.
 */
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private Picture photo;
    private Picture transpose_photo;//列优先
    private double[][] energy;
    private int[][] edgeto;
    private double[][] distto;

    public SeamCarver(Picture picture) {
        // create a seam carver object based on the given picture
        if(picture==null) throw  new IllegalArgumentException();
        photo = new Picture(picture);

    }

    public Picture picture() {
        // current picture
        return new Picture(photo);
    }

    public int width() {
        // width of current picture
        return photo.width();
    }

    public int height() {
        // height of current picture
        return photo.height();
    }

    private int rgb_r(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int rgb_g(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int rgb_b(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    public double energy(int x, int y) {
        // energy of pixel at column x and row y
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height())
            throw new IllegalArgumentException("wrong Argument");
        if (x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1) return 1000;
        int prev_x = photo.getRGB(x - 1, y);
        int after_x = photo.getRGB(x + 1, y);
        int prev_y = photo.getRGB(x, y - 1);
        int after_y = photo.getRGB(x, y + 1);
        double devi_x = Math.pow(rgb_r(prev_x) - rgb_r(after_x), 2) + Math.pow(rgb_g(prev_x) - rgb_g(after_x), 2) + Math.pow(rgb_b(prev_x) - rgb_b(after_x), 2);
        double devi_y = Math.pow(rgb_r(prev_y) - rgb_r(after_y), 2) + Math.pow(rgb_g(prev_y) - rgb_g(after_y), 2) + Math.pow(rgb_b(prev_y) - rgb_b(after_y), 2);
        return Math.sqrt(devi_x + devi_y);

    }

    private void relax_x(int i, int j, int m, int n) {
        if(m-i<=1 && n-j<=1) {
            if (distto[m][n] > distto[i][j] + energy[m][n]) {
                distto[m][n] = distto[i][j] + energy[m][n];
                edgeto[m][n] = j;
            }
        }
    }

    private void traverse() {
        Picture traverse = new Picture(this.height(), this.width());
        for (int col = 0; col < this.width(); col++)
            for (int row = 0; row < this.height(); row++)
                traverse.setRGB(row, col, photo.getRGB(col, row));
        photo = traverse;
    }

    public int[] findVerticalSeam() {
        // sequence of indices for horizontal seam
        energy = new double[this.height()][this.width()];
        edgeto = new int[this.height()][this.width()];
        distto = new double[this.height()][this.width()];
        int row = energy.length;
        int col = energy[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                energy[i][j] = this.energy(j, i);
                distto[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < energy[0].length; i++) {
            edgeto[0][i] = i;
            distto[0][i] = 0;
        }
        for (int i = 0; i < row-1; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) {
                    relax_x(i, j, i + 1, j);
                    if(j+1<col) {
                        relax_x(i, j, i + 1, j + 1);
                    }
                } else if (j == col - 1) {
                    relax_x(i, j, i + 1, j);
                    if(j-1>=0) {
                        relax_x(i, j, i + 1, j - 1);
                    }
                } else {
                    relax_x(i, j, i + 1, j);
                    if(j+1 < col) {
                        relax_x(i, j, i + 1, j + 1);
                    }
                    if(j-1 >=0) {
                        relax_x(i, j, i + 1, j - 1);
                    }
                }
            }
        }
        double min_point = Double.MAX_VALUE;
        int point = -1;
        for (int i = 0; i < col; i++) {
            if (distto[row-1][i] < min_point) {
                min_point = distto[row-1][i];
                point = i;
            }
        }
        int[] res = new int[energy.length];
        Stack<Integer> path = new Stack<>();
        path.push(point);
        int start_x = row - 1;
        int start_y = point;
        while (start_x != 0) {
            path.push(edgeto[start_x][start_y]);
            start_y = edgeto[start_x][start_y];
            start_x -= 1;
        }
        int index = 0;
        for (int x : path) {
            res[index++] = x;
        }
        return res;

    }

    public int[] findHorizontalSeam() {
        // sequence of indices for vertical seam
        int[] res;
        traverse();
        res = this.findVerticalSeam();
        traverse();
        return res;

    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
        if(seam == null) throw new IllegalArgumentException("null");
        if(seam.length!=this.width()) throw  new IllegalArgumentException() ;
        for(int i=0;i<seam.length;i++){
            if(seam[i]<0 || seam[i]>=this.height()) throw new IllegalArgumentException();
            if(i!=0){
                if(Math.abs(seam[i-1]-seam[i])>1) throw new IllegalArgumentException();
            }
        }
        Picture rem_photo = new Picture(this.width(), this.height() - 1);
        int index = 0;
        for (int col = 0; col < rem_photo.width(); col++) {
            for (int row = 0; row < rem_photo.height(); row++) {
                if (row < seam[index]) {
                    rem_photo.setRGB(col, row, photo.getRGB(col, row));
                } else {
                    rem_photo.setRGB(col, row, photo.getRGB(col, row + 1));
                }
            }
            index++;
        }
        photo = rem_photo;
    }


    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from current picture
        if(seam == null) throw new IllegalArgumentException("null");
        if(seam.length!=this.height()) throw  new IllegalArgumentException() ;
        for(int i=0;i<seam.length;i++){
            if(seam[i]<0 || seam[i]>=this.width()) throw new IllegalArgumentException();
            if(i!=0){
                if(Math.abs(seam[i-1]-seam[i])>1) throw new IllegalArgumentException();
            }
        }
        traverse();
        removeHorizontalSeam(seam);
        traverse();

    }
}
