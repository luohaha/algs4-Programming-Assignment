import java.util.Iterator;
import java.util.TreeSet;


public class PointSET{
	private TreeSet<Point2D> pointSet;
	public PointSET() {
		pointSet = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}
	
	public int size() {
		return pointSet.size();
	}
	
	public void insert(Point2D p) {
		this.pointSet.add(p);
	}
	
	public boolean contains(Point2D p) {
		return this.pointSet.contains(p);
	}
	
	public void draw() {
		for (Point2D p : this.pointSet) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		return new Point2DIterable(rect);
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) throw new NullPointerException();
		if (isEmpty()) return null;
		double d = -1;
		Point2D res = null;
		for (Point2D each : this.pointSet) {
			if (p == each) continue;
			if (d < 0 || p.distanceSquaredTo(each) < d) {
				res = each;
				d = p.distanceSquaredTo(each);
			}
		}
		return res;
	}
	
	/*
	 * 返回
	 * */
	private class Point2DIterable implements Iterable<Point2D> {
		private TreeSet<Point2D> contain;
		public Point2DIterable(RectHV rect) {
			if (rect == null) throw new NullPointerException();
			contain = new TreeSet<Point2D>();	
			for (Point2D p : pointSet) {
				if (rect.contains(p)) {
					contain.add(p);
				}
			}
		}
		
		public Iterator<Point2D> iterator() {
			return contain.iterator();
		}
	}
	
	public static void main(String[] args) {
		PointSET pp = new PointSET();
		In in = new In(args[0]);
		double a, b;
		while (!in.isEmpty()) {
			a = in.readDouble();
			b = in.readDouble();
			Point2D p = new Point2D(a, b);
			pp.insert(p);
		}
		  StdDraw.setXscale(0, 4);
	      StdDraw.setYscale(0, 4);
	      StdDraw.setPenColor(StdDraw.RED);
	      StdDraw.setPenRadius(.02);
		pp.draw();
		RectHV rect = new RectHV(0, 0, 0.5, 0.5);
	      StdDraw.setPenColor(StdDraw.BLUE);
	     rect.draw();
	     for (Point2D ppp : pp.range(rect)) {
	    	 ppp.draw();
	     }
		StdDraw.show();
	}
}
