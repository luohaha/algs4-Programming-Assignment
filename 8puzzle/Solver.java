import java.util.Comparator;


public class Solver {
	private final Comparator<Node> ByManhattan = new ByManhattan();
	private Node goal = null;
	private MinPQ<Node> queue = new MinPQ<Node>(ByManhattan);
	private MinPQ<Node> twinQueue = new MinPQ<Node>(ByManhattan);
	private boolean isSolvable = true;//默认是可以有解的
	public Solver(Board initial) {
		if (initial == null) throw new NullPointerException();
		this.queue.insert(new Node(initial, null, 0));
		this.twinQueue.insert(new Node(initial.twin(), null, 0));
		boolean solved = false;
		while(!solved) {
			solved = solve();
		}
	}
	
	public boolean isSolvable(){
		return isSolvable;
	}
	
	public int moves() {
		if (!isSolvable)
			return -1;
		
		return this.goal.stepCount;
	}
	
	public Iterable<Board> solution() {
		if (!isSolvable) {
			return null;
		}
		Stack<Board> stack = new Stack<Board>();
		Node tmp = this.goal;
		while(tmp != null) {
			stack.push(tmp.board);
			tmp = tmp.prev;
		}
		return stack;
	}
	
	public static void main(String[] args) {
		 In in = new In(args[0]);
		 int N = in.readInt();
		 int[][] blocks = new int[N][N];
		 for (int i = 0; i < N; i++)
			 for (int j = 0; j < N; j++)
				 blocks[i][j] = in.readInt();
		 Board initial = new Board(blocks);
		 
		// solve the puzzle
		 Solver solver = new Solver(initial);
		 // print solution to standard output
		 if (!solver.isSolvable())
			 StdOut.println("No solution possible");
		 else {
			 StdOut.println("Minimum number of moves = " + solver.moves());
			 for (Board board : solver.solution())
				 StdOut.println(board);
		 	}
	}
	/*
	 * 让两种同时开始，谁先结束，谁就是有解的
	 * */
	private boolean solve() {
		Node first = queue.delMin();
		
		if (first.board.isGoal()) {
			this.goal = first;
			return true;
		}
		
		for (Board b : first.board.neighbors()) {
			Node node = new Node(b, first, first.stepCount+1);
			if (node.equals(node.prev)) {
				continue;
			}
			queue.insert(node);
		}
		
		Node second = twinQueue.delMin();
		
		if (second.board.isGoal()) {
			isSolvable = false;
			return true;
		}
		
		for (Board b : second.board.neighbors()) {
			Node node = new Node(b, second, second.stepCount+1);
			if (node.equals(node.prev)) {
				continue;
			}
			twinQueue.insert(node);
		}
		
		return false;
	}
	
	/*
	 * 以曼哈顿值的大小来排序
	 * */
	private class ByManhattan implements Comparator<Node> {
		public int compare(Node a, Node b) {
			return a.board.manhattan() - b.board.manhattan();
		}
	}
	
	private class Node {
		private Board board;
		private Node prev;
		private int stepCount;
		
		public Node(Board now, Node prev, int count) {
			this.board = now;
			this.prev = prev;
			this.stepCount = count;
		}
		public boolean equals(Object y) {
			if (y == null) return false;
			if (y == this) return true;
			if (y.getClass() != this.getClass()) return false;
			
			Node that = (Node) y;
			if (that.board.equals(this.board)) {
				return true;
			} else {
				return false;
			}
		}
		

		public int hashCode() {
			
			int hash = board.toString().hashCode();
			return hash;
		}
	}
}
