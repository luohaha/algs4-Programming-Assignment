import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class Brute {
	public static void main(String[] args) {
		// rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
    	In in = new In(args[0]);
    	int num = in.readInt();
    	Point[] point = new Point[num];
    	for (int i = 0; i < num; i++) {
    		int x = in.readInt();
    		int y = in.readInt();
    		point[i] = new Point(x, y);
    		point[i].draw();
    	}

        
        /*
         * find points in line
         * */
    	for (int i = 0; i < num-3; i++) {
    		for (int j = i+1; j < num-2; j++) {
    			//i to j
    			double toj = point[i].slopeTo(point[j]);
    			for (int k = j+1; k < num - 1; k++) {
    				//i to k
    				double tok = point[i].slopeTo(point[k]);
    				if (toj == tok) {
        				for (int m = k+1; m<num; m++) {
        					double tom = point[i].slopeTo(point[m]);
        					if (tom == toj) {
        						List<Point> pointList = new ArrayList<Point>();
        						pointList.add(point[i]);
        						pointList.add(point[j]);
        						pointList.add(point[k]);
        						pointList.add(point[m]);
        						Collections.sort(pointList);
        						for (int ii = 0; ii < 3; ii++) {
        							StdOut.print(pointList.get(ii));
        							StdOut.print(" -> ");
        						}
        						StdOut.println(pointList.get(3));
        						pointList.get(0).drawTo(pointList.get(3));
        					}
        					
        				}
    				}

    			}
    		}
    	}
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
	}
}
