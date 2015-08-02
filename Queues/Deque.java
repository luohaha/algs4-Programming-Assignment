import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * 双端队列
 * */
public class Deque<Item> implements Iterable<Item> {
	private int N;//队列中元素的个数
	private Node first;//队列中第一个
	private Node last;//队列中最后一个
	private class Node{
		//双端队列的节点，可以指向前端和后端
		private Item value;
		private Node next;
		private Node prev;
		public Node(Item value, Node next, Node prev) {
			this.value = value;
			this.next = next;
			this.prev = prev;
		}
	}
	//初始化
	public Deque() {
		first = null;
		N = 0;
		last = null;
	}
	public boolean isEmpty() {
		if (N == 0)
			return true;
		else
			return false;
	}
	
	public int size() {
		return N;
	}
	
	public void addFirst(Item item) {
		if (item == null) throw new NullPointerException();
		Node newItem;
		if (isEmpty()) {
			newItem = new Node(item, null, null);
		    first = newItem;
		    last = newItem;
		} else {
			newItem = new Node(item, first, null);
			first.prev = newItem;
			first = newItem;
		}
		N++;
	}
	
	public void addLast(Item item) {
		if (item == null) throw new NullPointerException();
		Node newItem;
		if (isEmpty()) {
			newItem = new Node(item, null, null);
		    first = newItem;
		    last = newItem;
		} else {
			newItem = new Node(item, null, last);
			last.next = newItem;
			last = newItem;
		}
		N++;
	}
	
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException();
		Item res = first.value;
		Node newFirst = first.next;
		if (newFirst == null) {
			first = last = null;
			N--;
			return res;
		}
		newFirst.prev = null;
		first.next = null;
		first = newFirst;
		N--;
		return res;
	}
	
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException();
		Item res = last.value;
		Node newLast = last.prev;
		if (newLast == null) {
			first = last = null;
			N--;
			return res;
		}
		newLast.next = null;
		last.prev = null;
		last = newLast;
		N--;
		return res;
	}
	
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		
		public DequeIterator() {
			current = first;
		}
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.value;
			current = current.next;
			return item;
		}
		
	}
	
	public static void main(String[] args) {
		Deque<String> d = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("exit")) d.addFirst(item);
			else if (!d.isEmpty()) StdOut.print(d.removeFirst() + " ");
		}
	}
}
