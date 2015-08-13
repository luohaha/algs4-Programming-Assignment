import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;


public class KdTree {
	private final boolean Ve = true;
	private final boolean Ho = false;
	private class Node {

		private Point2D point;
		private boolean pos;
		private Node left;
		private Node right;
		
		public Node(Point2D p, boolean pos, Node left, Node right) {
			this.point = p;
			this.pos = pos;
			this.left = left;
			this.right = right;
		}
	}
	private class NodeTree{
		private Node node;
		private int size;
	}
	private NodeTree root;
	private double disTmp;
	private Point2D tmpPoint;
	public KdTree() {
		root = new NodeTree();
		root.node = null;
		root.size = 0;
	}
	
	public boolean isEmpty() {
		return (root.size == 0);
	}
	
	public int size() {
		return root.size;
	}
	
	public void insert(Point2D p) {
		if (root.node == null) {
			Node newNode = new Node(p, Ve, null, null);
			root.node = newNode;
			root.size=1;
			return;
		}
		root.size++;
		Node search = root.node;
		while (search != null) {
			if (search.pos == Ve) {
				//如果是垂直的线，则比较x
				if (p.x() < search.point.x()) {
					//左边
					if (search.left == null) {
						Node newNode = new Node(p, !search.pos, null, null);
						search.left = newNode;
						break;
					} else
						search = search.left;
				} else {
					//右边
					if (search.right == null) {
						Node newNode = new Node(p, !search.pos, null, null);
						search.right = newNode;
						break;
					} else
						search = search.right;
				}
			} else {
				//如果是水平的线
				if (p.y() < search.point.y()) {
					if (search.left == null) {
						//下面
						Node newNode = new Node(p, !search.pos, null, null);
						search.left = newNode;
						break;
					} else
						search = search.left;
				} else {
					if (search.right == null) {
						Node newNode = new Node(p, !search.pos, null, null);
						search.right = newNode;
						break;
					} else
						search = search.right;			
				}
			}
		}//while
		
		return;
	}
	
	public boolean contains(Point2D p) {
		Node search = root.node;
		while (search != null) {
			if (search.point.equals(p)) {
				return true;
			}
			if (search.pos == Ve) {
				//垂直的
				if (p.x() < search.point.x()) {
					search = search.left;
				} else {
					search = search.right;
				}
			} else {
				//水平的
				if (p.y() < search.point.y()) {
					search = search.left;
				} else {
					search = search.right;
				}
			}
		}//while
		return false;
	}
	
	public void draw() {
		drawEach(root.node);
	}
	
	private void drawEach(Node node) {
		if (node == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.setPenRadius(.02);
		node.point.draw();
		if (node.pos == Ve) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(.002);
			StdDraw.line(node.point.x(), 0, node.point.x(), 1);
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius(.002);
			StdDraw.line(0, node.point.y(), 1, node.point.y());
		}
		
		drawEach(node.left);
		drawEach(node.right);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		return new Point2DIterable(rect);
	}
	
	public Point2D nearest(Point2D p) {
		if (root.node == null) return null;
		disTmp = -1;
		tmpPoint = null;
		findNearest(root.node, p);
		return tmpPoint;
	}
	
	public static void main(String[] args) {
		KdTree pp = new KdTree();
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
	      StdDraw.line(0, 0, 0, 1);
	      StdDraw.line(0, 1, 1, 1);
	      StdDraw.line(0, 0, 1, 0);
	      StdDraw.line(1, 0, 1, 1);
	      StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.002);
	      //pp.draw();
		 Point2D ppp = pp.nearest(new Point2D(0.03, 0.65));
		 StdOut.println(ppp.x()+"    "+ppp.y());
	      StdDraw.show();
	}
	
	private void findNearest(Node node, Point2D p) {
		if (node == null) return;
		double get = p.distanceTo(node.point);
		if (disTmp < 0 || get < disTmp) {
			disTmp = get;
			this.tmpPoint = node.point;
		}
		if (node.pos == Ve) {
			//垂直
			if ((p.x() - node.point.x()) < 0) {
				if (node.left == null) return;
				findNearest(node.left, p);
				if (node.point.x() - p.x() < this.disTmp) {
					if (node.right == null) return;
					findNearest(node.right, p);
				}
			} else if (p.x() - node.point.x() > 0) {
				if (node.right == null) return;
				findNearest(node.right, p);
				if (p.x() - node.point.x() < this.disTmp) {
					if (node.left == null) return;
					findNearest(node.left, p);
				}
			}
		} else {
			//水平
			if ((p.y() - node.point.y()) < 0) {
				if (node.left == null) return;
				findNearest(node.left, p);
				if (node.point.y() - p.y() < this.disTmp) {
					if (node.right == null) return;
					findNearest(node.right, p);
				}
			} else if (p.y() - node.point.y() > 0) {
				if (node.right == null) return;
				findNearest(node.right, p);
				if (p.y() - node.point.y() < this.disTmp) {
					if (node.left == null) return;
					findNearest(node.left, p);
				}
			}
		}
	}
	
	private class Point2DIterable implements Iterable<Point2D> {
		private TreeSet<Point2D> set;
		public Point2DIterable(RectHV rect) {
			set = new TreeSet<Point2D>();
			find(rect, root.node);
		}
		
		private void find(RectHV rect, Node p) {
			if (p == null) return;
			if (rect.contains(p.point)) {
				set.add(p.point);
			}
			
			if (p.pos == Ve) {
				//垂直的
				if (rect.xmax() < p.point.x()) {
					find(rect, p.left);
				} else if (rect.xmin() > p.point.x()) {
					find(rect, p.right);
				} else {
					find(rect, p.left);
					find(rect, p.right);
				}
			} else {
				//水平的
				if (rect.ymax() < p.point.y()) {
					find(rect, p.left);
				} else if (rect.ymin() > p.point.y()) {
					find(rect, p.right);
				} else {
					find(rect, p.left);
					find(rect, p.right);
				}
			}

		}
		
		public Iterator<Point2D> iterator() {
			return set.iterator();
		}
	}
}
