import java.awt.Color;


public class SeamCarver {
	private Picture picture;
	private int edgeTo[];
	private double energe[];
	private double dist[];
	private int N;
	public SeamCarver(Picture picture) {
		this.picture = picture;
	}
	
	public Picture picture() {
		return this.picture;
	}
	
	public int width() {
		return this.picture.width();
	}
	
	public int height() {
		return this.picture.height();
	}
	
	public  double energy(int x, int y) {
		if (x < 0 || x >= width() || y < 0 || y >= height()) {
			throw new IndexOutOfBoundsException();
		}
		if (x == 0 || x == width() - 1 || y == 0 || y == height()-1) {
			return 1000;
		}
		return calEnergy(x, y);
	}
	
	private double calEnergy(int x, int y) {
		if (x < 0 || x >= width()) throw new IndexOutOfBoundsException();
		if (y < 0 || y >= height()) throw new IndexOutOfBoundsException();
		Color up = this.picture.get(x, y-1);
		Color down = this.picture.get(x, y+1);
		Color left = this.picture.get(x-1, y);
		Color right = this.picture.get(x+1, y);
		double dx2 = (right.getRed() - left.getRed()) * (right.getRed() - left.getRed()) + 
				(right.getBlue() - left.getBlue()) * (right.getBlue() - left.getBlue()) +
				(right.getGreen() - left.getGreen()) * (right.getGreen() - left.getGreen());
		double dy2 = (down.getRed() - up.getRed()) * (down.getRed() - up.getRed()) + 
				(down.getBlue() - up.getBlue()) * (down.getBlue() - up.getBlue()) +
				(down.getGreen() - up.getGreen()) * (down.getGreen() - up.getGreen());
		return Math.sqrt(dx2 + dy2);
	}
	
	public int[] findHorizontalSeam() {
		this.N = height() *width();
		/*
		 * initial
		 * */
		edgeTo = (int[]) new int[this.N];
		energe  = (double[]) new double[this.N];
		dist = (double[]) new double[this.N];
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < height(); y++) {
				int now = exchange(x, y);
				if (x == 0) {
					dist[now] = 0;
				} else {
					dist[now] = Double.POSITIVE_INFINITY;
				}
				edgeTo[now] = -1;
				energe[now] = energy(x, y);
			}
		}
		
		/*
		 * search
		 * */
		for (int x = 0; x < width() - 1; x++) {
			for (int y = 0; y < height(); y++) {
				if (y > 0) {
					relax(exchange(x, y), exchange(x+1, y-1));
				}
				relax(exchange(x, y), exchange(x+1, y));
				if (y < height() - 1) {
					relax(exchange(x, y), exchange(x+1, y+1));
				}
			}
		}
		
		int miny = 0;
		double min = Double.POSITIVE_INFINITY;
		int index;
		for (int i = 0; i < height(); i++) {
			if (this.dist[exchange(width()-1, i)] < min) {
				miny = i;
				min = this.dist[exchange(width()-1, i)];
			}
		}
		index = exchange(width()-1, miny);
		int[] path = new int[width()];
		
		while (index >= 0) {
			int y = index / width();
			int x = index % width();
			path[x] = y;
			index = edgeTo[index];
		}
		
		return path;
	}
	
	public int[] findVerticalSeam() {
		this.N = height() *width();
		/*
		 * initial
		 * */
		edgeTo = (int[]) new int[this.N];
		energe  = (double[]) new double[this.N];
		dist = (double[]) new double[this.N];
		for (int y = 0; y < height(); y++) {
			for (int x = 0; x < width(); x++) {
				int now = exchange(x, y);
				if (y == 0) {
					dist[now] = 0;
				} else {
					dist[now] = Double.POSITIVE_INFINITY;
				}
				edgeTo[now] = -1;
				energe[now] = energy(x, y);
			}
		}
		
		/*
		 * search
		 * */
		for (int y = 0; y < height()-1; y++) {
			for (int x = 0; x < width(); x++) {
				if (x > 0) {
					relax(exchange(x, y), exchange(x-1, y+1));
				}
				relax(exchange(x, y), exchange(x, y+1));
				if (x < width() - 1) {
					relax(exchange(x, y), exchange(x+1, y+1));
				}
			}
		}
		
		int minx = 0;
		double min = Double.POSITIVE_INFINITY;
		int index;
		for (int i = 0; i < width(); i++) {
			if (this.dist[exchange(minx, height()-1)] < min) {
				minx = i;
				min = this.dist[exchange(minx, height()-1)];
			}
		}
		index = exchange(minx, height()-1);
		int[] path = new int[height()];
		
		while (index >= 0) {
			int y = index / width();
			int x = index % width();
			path[y] = x;
			index = edgeTo[index];
		}
		
		return path;
	}
	
	public void removeHorizontalSeam(int[] seam) {
		if (height() <= 1)  throw new java.lang.IllegalArgumentException();
		if (seam.length != width())  throw new java.lang.IllegalArgumentException();
		
		Picture result = new Picture(width(), height() - 1);
		for (int x = 0; x < width(); x++) {
			int mid = seam[x];
			for (int y = 0; y < height()-1; y++) {
				if (y < mid) {
					result.set(x, y, this.picture.get(x, y));
				} else {
					result.set(x, y, this.picture.get(x, y+1));
				}
			}
		}
		this.picture = result;
		this.dist = null;
		this.edgeTo = null;
		this.energe = null;
	}
	
	public void removeVerticalSeam(int[] seam) {
		if (width() <= 1)  throw new java.lang.IllegalArgumentException();
		if (seam.length != height())  throw new java.lang.IllegalArgumentException();
		
		Picture result = new Picture(width()-1, height());
		for (int y = 0; y < height(); y++) {
			int mid = seam[y];
			for (int x = 0; x < width()-1; x++) {
				if (x < mid) {
					result.set(x, y, this.picture.get(x, y));
				} else {
					result.set(x, y, this.picture.get(x+1, y));
				}
			}
		}
		this.picture = result;
		this.dist = null;
		this.edgeTo = null;
		this.energe = null;
	}
	
	/*
	 * 松弛计算
	 * */
	private void relax(int from, int to) {
		int y = to / width();
		int x = to % width();
		double ene = this.energy(x, y);
		if (this.dist[to] > this.dist[from] + ene) {
			this.dist[to] = this.dist[from] + ene;
			this.edgeTo[to] = from;
		}
	}
	
	private int exchange(int x, int y) {
		return x + y * width();
	}
	
}
