import java.util.ArrayList;
import java.util.List;


public class SAP {
	 private Digraph graph;
	 // constructor takes a digraph (not necessarily a DAG)
	   public SAP(Digraph G) {
		   if (G == null) throw new NullPointerException();
		   this.graph = G;
	   }
	   
	   // throw an IndexOutOfBoundsException unless 0 <= v < V
	    private void validateVertex(int v) {
	        if (v < 0 || v >= this.graph.V())
	            throw new IndexOutOfBoundsException();
	    }
	   
	   // length of shortest ancestral path between v and w; -1 if no such path
	   public int length(int v, int w) {
		   validateVertex(v);
		   validateVertex(w);
		   BreadthFirstDirectedPaths pathv = new BreadthFirstDirectedPaths(this.graph, v);
		   BreadthFirstDirectedPaths pathw = new BreadthFirstDirectedPaths(this.graph, w);
		   int shortest = Integer.MAX_VALUE;
		   int ancestor = -1;
		   List<Integer> list = new ArrayList<Integer>();
		   for (int i = 0; i < this.graph.V(); i++) {
			   if (pathv.hasPathTo(i) && pathw.hasPathTo(i)) {
				   list.add(i);
			   }
		   }
		   
		   for (Integer i : list) {
			   int dis = pathv.distTo(i) + pathw.distTo(i);
			   if (dis < shortest) {
				   shortest = dis;
				   ancestor = i;
			   }
		   }
		   if (ancestor == -1)
			   return -1;
		   return shortest;
	   }
	   
	   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	   public int ancestor(int v, int w) {
		   validateVertex(v);
		   validateVertex(w);
		   BreadthFirstDirectedPaths pathv = new BreadthFirstDirectedPaths(this.graph, v);
		   BreadthFirstDirectedPaths pathw = new BreadthFirstDirectedPaths(this.graph, w);
		   int shortest = Integer.MAX_VALUE;
		   int ancestor = -1;
		   List<Integer> list = new ArrayList<Integer>();
		   for (int i = 0; i < this.graph.V(); i++) {
			   if (pathv.hasPathTo(i) && pathw.hasPathTo(i)) {
				   list.add(i);
			   }
		   }
		   
		   for (Integer i : list) {
			   int dis = pathv.distTo(i) + pathw.distTo(i);
			   if (dis < shortest) {
				   shortest = dis;
				   ancestor = i;
			   }
		   }
		   
		   return ancestor;
	   }
	   
	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	   public int length(Iterable<Integer> v, Iterable<Integer> w) {
		   for (Integer i : v) {
			   validateVertex(i);
		   }
		   for (Integer i : w) {
			   validateVertex(i);
		   }
		   BreadthFirstDirectedPaths pathv = new BreadthFirstDirectedPaths(this.graph, v);
		   BreadthFirstDirectedPaths pathw = new BreadthFirstDirectedPaths(this.graph, w);
		   int shortest = Integer.MAX_VALUE;
		   int ancestor = -1;
		   List<Integer> list = new ArrayList<Integer>();
		   for (int i = 0; i < this.graph.V(); i++) {
			   if (pathv.hasPathTo(i) && pathw.hasPathTo(i)) {
				   list.add(i);
			   }
		   }
		   
		   for (Integer i : list) {
			   int dis = pathv.distTo(i) + pathw.distTo(i);
			   if (dis < shortest) {
				   shortest = dis;
				   ancestor = i;
			   }
		   }
		   if (ancestor == -1)
			   return -1;
		   return shortest;
	   }
	   
	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		   for (Integer i : v) {
			   validateVertex(i);
		   }
		   for (Integer i : w) {
			   validateVertex(i);
		   }
		   BreadthFirstDirectedPaths pathv = new BreadthFirstDirectedPaths(this.graph, v);
		   BreadthFirstDirectedPaths pathw = new BreadthFirstDirectedPaths(this.graph, w);
		   int shortest = Integer.MAX_VALUE;
		   int ancestor = -1;
		   List<Integer> list = new ArrayList<Integer>();
		   for (int i = 0; i < this.graph.V(); i++) {
			   if (pathv.hasPathTo(i) && pathw.hasPathTo(i)) {
				   list.add(i);
			   }
		   }
		   
		   for (Integer i : list) {
			   int dis = pathv.distTo(i) + pathw.distTo(i);
			   if (dis < shortest) {
				   shortest = dis;
				   ancestor = i;
			   }
		   }
		   
		   return ancestor;
	   }
	   
	   // do unit testing of this class
	   public static void main(String[] args) {
		   In in = new In(args[0]);
		    Digraph G = new Digraph(in);
		    SAP sap = new SAP(G);
		    while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = sap.length(v, w);
		        int ancestor = sap.ancestor(v, w);
		        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		    }
	   }
}
