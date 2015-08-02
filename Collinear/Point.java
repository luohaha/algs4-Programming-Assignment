
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
    	public int compare(Point a, Point b) {
    		if (a == null || b ==null) throw new NullPointerException();
    		double r1 = slopeTo(a);
    		double r2 = slopeTo(b);
    		if (r1 > r2) {
    			return 1;
    		} else if (r1 < r2) {
    			return -1;
    		} else {
    			return 0;
    		}
    	}
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	if (that == null) throw new NullPointerException();
    	return comp(that, this);	
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if (this.y > that.y) {
    		return 1;
    	} else if (this.y < that.y) {
    		return -1;
    	} else if (this.x > that.x) {
    		return 1;
    	} else if (this.x < that.x) {
    		return -1;
    	} else {
    		return 0;
    	}
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
    /*
     * 比较x与y的大小，使用斜率为度量
     * */
    private double comp(Point x, Point y) {
    	int dx = y.x - x.x;
    	int dy = y.y - x.y;
    	if (dx == 0 && dy == 0) {
    		return Double.NEGATIVE_INFINITY;
    	} else if (dx == 0) {
    		return Double.POSITIVE_INFINITY;
    	} else if (dy == 0) {
    		return +0.0;
    	} else {
    		return (double)dy / (double)dx;
    	}
    }
}

