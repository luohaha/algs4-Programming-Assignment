
public class Board {
	private int N;
	private int[][] blocks;
	
	public Board(int[][] blocksParam) {
		if (blocksParam == null) throw new NullPointerException();
		this.N = blocksParam.length;
		blocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				this.blocks[i][j] = blocksParam[i][j];
			}
		}
	}
	
	public int dimension() {
		return this.N;
	}
	
	public int hamming() {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] == 0) {
					continue;
				}
				if ((i*N+j+1) != this.blocks[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int manhattan() {
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] == 0)
					continue;
				sum += distance(j, i);
			}
		}
		return sum;
	}
	
	public boolean isGoal() {
		return manhattan() == 0;
	}
	
	public Board twin() {
		int[][] twinBlocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				twinBlocks[i][j] = blocks[i][j];
			}
		}
		
		while (true) {
			/*
			 * 要交换的结点
			 * */
			int row = StdRandom.uniform(N);
			int col = StdRandom.uniform(N);
			if (col == 0)
				continue;
			if (twinBlocks[row][col] == 0 || twinBlocks[row][col-1] == 0)
				continue;
			int tmp = twinBlocks[row][col];
			twinBlocks[row][col] = twinBlocks[row][col-1];
			twinBlocks[row][col-1] = tmp;
			break;
		}
		
		return new Board(twinBlocks);
	}
	
	public boolean equals(Object y) {
		if (y == null) return false;
		if (y == this) return true;
		if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		
		if (that.N != this.N) return false;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (that.blocks[i][j] != this.blocks[i][j])
					return false;
			}
		}
		
		return true;
	}
	
	public Iterable<Board> neighbors() {
		//用stack是因为stack已经实现了Iterable
		Stack<Board> stack = new Stack<Board>();
	
		int[] zero = new int[2];
		zero = findZero();
		if (zero[0] != 0) {
			stack.push(change(this, zero[0]-1, zero[1], zero[0], zero[1]));
		}
		if (zero[0] < N-1) {
			stack.push(change(this, zero[0], zero[1], zero[0]+1, zero[1]));
		}
		if (zero[1] != 0) {
			stack.push(change(this, zero[0], zero[1]-1, zero[0], zero[1]));
		}
		if (zero[1] < N-1) {
			stack.push(change(this, zero[0], zero[1], zero[0], zero[1]+1));
		}
		
		return stack;
	}
	
	 public String toString(){
		 StringBuilder builder = new StringBuilder();
		 builder.append(N+"\n");
		 for (int i = 0; i < N; i++) {
			 for (int j = 0; j < N; j++) {
				 builder.append(String.format("%2d ", this.blocks[i][j]));
			 }
			 builder.append("\n");
		 }
		 return new String(builder);
	 }
	 
	 public static void main(String[] args) {
		 int[][] blocks3 = new int[][] {
				 {8, 1, 3},
				 {4, 0, 2},
				 {7, 6, 5}
				 };
				 Board board3 = new Board(blocks3);
				// assertEquals(board3.manhattan(), 10);
				 StdOut.println(board3.hamming());
				 int[][] blocks4 = new int[][] {
				 {1, 3, 2, 4},
				 {5, 6, 7, 8},
				 {9, 10, 11, 12},
				 {13, 14, 15, 0}
				 };
				 Board board4 = new Board(blocks4);
				 //assertEquals(board4.manhattan(), 2);
				 StdOut.println(board4.manhattan());
				 int[][] blockPuzzle04 = new int [][] {
				 {0, 1, 3},
				 {4, 2, 5},
				 {7, 8, 6}
				 };
				 Board boardPuzzle04 = new Board(blockPuzzle04);
				 //assertEquals(boardPuzzle04.manhattan(), 4);
				 StdOut.println(boardPuzzle04.manhattan());
	 }
	
	/*
	 * 创建一个新的结点，然后交换
	 * */
	private Board change(Board now, int x1, int y1, int x2, int y2) {
		int[][] res = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				res[i][j] = now.blocks[i][j];
			}
		}
		int tmp = res[y1][x1];
		res[y1][x1] = res[y2][x2];
		res[y2][x2] = tmp;
		
		return new Board(res);
	}
	/*
	 * 找到空格的点所在的x与y
	 * */
	private int[] findZero() {
		int[] xy = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] == 0) {
					xy[0] = j; //x轴
					xy[1] = i; //y轴
					return xy;
				}
			}
		}
		return null;
	}
	
	/*
	 * 在blocks中坐标为x,y的点，距离目标的距离
	 * */
	private int distance(int x, int y) {
		int val = blocks[y][x];
		int yy = (val - 1) / N;
		int xx = (val - 1) % N;
		return Math.abs(yy - y) + Math.abs(xx - x);
	}
}
