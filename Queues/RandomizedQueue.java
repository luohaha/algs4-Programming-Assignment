import java.util.Iterator;
import java.util.NoSuchElementException;


/*
 * 
 * */
public class RandomizedQueue<Item> implements Iterable<Item> {
	private int N;//元素个数
	private Item[] queue;
	
	public RandomizedQueue() {
		queue = (Item[]) new Object[1];
	}
	
	public boolean isEmpty() {
		return (N == 0) ? true : false;
	}
	
	public int size() {
		return N;
	}
	
	private void reSize(int size) {
		Item[] newQueue = (Item[]) new Object[size];
		for (int i = 0; i < N; i++) {
			newQueue[i] = queue[i];
		}
		queue = newQueue;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
		if (N == queue.length) {
			reSize(N * 2);
		}
		queue[N] = item;
		N++;
	}
	
	public Item dequeue() {
		if (N == 0) throw new NoSuchElementException();
		Item res;
		java.util.Random r=new java.util.Random();
		int pos = r.nextInt(N);
		res = queue[pos];
		if (pos != N-1) {
			queue[pos] = queue[N-1];
		}
		queue[--N] = null;
		if (N > 0 && N <= queue.length / 4) {
			reSize(queue.length / 2);
		}
		return res;
	}
	
	public Item sample() {
		if (N == 0) throw new NoSuchElementException();
		java.util.Random r=new java.util.Random();
		int pos = r.nextInt(N);
		return queue[pos];
	}
	
	public Iterator<Item> iterator() {
		return new  RandomizedQueueIterator();
	}
	
	private class  RandomizedQueueIterator implements Iterator<Item> {
		private Item[] copyQueue = (Item[]) new Object[queue.length];
		private int copyN = N;
		
		public RandomizedQueueIterator() {
			for (int i = 0; i < queue.length; i++) {
				copyQueue[i] = queue[i];
			}
		}
		public boolean hasNext() {
			return copyN != 0;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			java.util.Random r=new java.util.Random();
			int pos = r.nextInt(copyN);
			Item item = copyQueue[pos];
			if (pos != copyN-1) {
				copyQueue[pos] = copyQueue[copyN-1];
			}
			copyQueue[copyN-1] = null;
			copyN--;
			return item;
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<String> d = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("exit")) d.enqueue(item);
			else {
				while(!d.isEmpty()) {
					StdOut.print(d.dequeue()+"\n");
				}
				break;
			}
		}
	}
	
}
