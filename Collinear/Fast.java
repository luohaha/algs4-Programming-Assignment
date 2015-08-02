import java.util.*;


public class Fast {
	public static void main(String[] args) {
		// rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
    	In in = new In(args[0]);
    	int num = in.readInt();
    	//Point[] point = new Point[num];
    	Point[] point = new Point[num];
    	Point[] pointsSlopes = new Point[num];
    	//get points
    	for (int i = 0; i < num; i++) {
    		int x = in.readInt();
    		int y = in.readInt();
    		point[i] = new Point(x, y);
    		point[i].draw();
    	}
    	
    	if (num >= 4) {
    		pointsSlopes = Arrays.copyOf(point, point.length);
    		for (Point originPoint : point) {
    			Arrays.sort(pointsSlopes, originPoint.SLOPE_ORDER);
    			findLines(pointsSlopes);
    		}
    	}
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
	}
	
	 private static void findLines(Point[] points) {
		 Point p = points[0];
		 Point[] lines = new Point[points.length];
		 lines[0] = p;
		 double previousSlope = p.slopeTo(points[1]);
		 int alignedPoints = 0;
		 for (int i = 1; i < points.length; i++) {
			 Point point = points[i];
			 double slope = p.slopeTo(point);
			 if (slope == previousSlope) {
				 lines[++alignedPoints] = point;
			 } else {
				 if (alignedPoints >= 3) {
					 showLine(lines, alignedPoints + 1);
				 }
				 alignedPoints = 1;
				 lines[1] = point;
			 }
			 previousSlope = slope;
		 	}
		 	if (alignedPoints >= 3) {
		 		showLine(lines, alignedPoints + 1);
		 	}
		 }
	 /*
	  * 显示直线
	  * */
		 private static void showLine(Point[] lines, int size) {
			 Arrays.sort(lines, 1, size);
			 if (lines[0].compareTo(lines[1]) < 0) {
				 StdOut.printf("%s", lines[0]);
				 for (int k = 1; k < size; k++) {
					 Point point = lines[k];
					 StdOut.printf(" -> %s", point);
				 }
				 StdOut.println();
				 lines[0].drawTo(lines[size - 1]);
			 }
		 }
}