package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(E item) {
		// if item is null
		if (item == null) {
			throw new NullPointerException();
		}

		// add a node if the list is empty, then add element
		if (head.next == tail && tail.previous == head) {
			Node newNode = new Node();
			newNode.previous = head;
			newNode.next = tail;
			// doubly part
			head.next = newNode;
			tail.previous = newNode;
			newNode.addItem(item);
			size++;
			return true;
		}

		// since we're adding to the end, if the tails previous node is full, create new
		// node, then add element
		if (tail.previous.count == nodeSize) {
			Node newerNode = new Node();
			newerNode.previous = tail.previous;
			newerNode.next = tail;
			// doubly part
			tail.previous.next = newerNode;
			tail.previous = newerNode;
			newerNode.addItem(item);
			size++;
			return true;
		}

		// if the node isn't full, then we add regularly
		if (tail.previous.count < nodeSize) {
			tail.previous.addItem(item);
			size++;
			return true;
		}

		return false;
	}

	@Override
	public void add(int pos, E item) {
		// if position is out of bounds
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}

		// if the list is empty, create a new node and call normal add metod
		if (tail.previous == head) {
			add(item);
		}

		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;

		// if offset = 0 and one of these cases occur
		if (offset == 0) {
			// if the node has a predesesor fewer than nodeSize elements
			// and isnt the head, put it in node's predesesor
			if (temp.previous.count < nodeSize && temp.previous != head) {
				temp.previous.addItem(item);
				size++;
				return;
			}
			// same thing for if node is the tail
			else if (temp == tail) {
				add(item);
				size++;
				return;
			}
		}

		// if there is space in node temp, put the element in n at offset off
		// while also shifting array elements if required
		if (temp.count < nodeSize) {
			temp.addItem(offset, item);
		}

		// if that doesn't work, perform split operation which moves the last m / 2
		// elements
		// new successor node
		else {
			Node newSuccessor = new Node();
			//halfway variable to minimize code reuse
			int halfway = nodeSize / 2;
			int i = 0;
			while (i < halfway) {
				newSuccessor.addItem(temp.data[halfway]);
				temp.removeItem(halfway);
				i++;
			}

			// add/reformat links
			Node oldSuccessor = temp.next;

			newSuccessor.previous = temp;
			newSuccessor.next = oldSuccessor;
			temp.next = newSuccessor;
			oldSuccessor.previous = newSuccessor;

			int displacement = nodeSize / 2;
			// if offset is less than or equal to nodeSize / 2 put item in existing node at
			// offset
			if (offset <= displacement) {
				temp.addItem(offset, item);
			}
			// if offset is greater than nodeSize / 2 put item in new node
			// at offset (ofset - nodesize/2)

			if (offset > displacement) {
				newSuccessor.addItem(offset - displacement, item);
			}
		}
		// size goes up since item was added
		size++;
	}

	@Override
	public E remove(int pos) {
		// if the position is out of bounds thow out of bounds exception
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}

		// get node information
		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;
		E nodeValue = temp.data[offset];

		//halfway variable to minimize code reuse
		int halfway = nodeSize / 2;

		// if the node is before the tail and has only one element, delete the node
		if (temp.next == tail && temp.count == 1) {
			// change links
			Node predecessor = temp.previous;
			predecessor.next = temp.next;
			temp.next.previous = predecessor;
			temp = null;
		}

		// if node is the last in the list or if n has more than nodeSize / 2 elements,
		// remove item from node and shift elements
		else if (temp.next == tail || temp.count > halfway) {
			temp.removeItem(offset);
		}

		// node must have less than nodeSize / 2 elements, but because we don't look at
		// predecessor,
		// go to successor and perform a merge to consolidate nodes
		else {
			temp.removeItem(offset);
			Node successor = temp.next;

			// check to see which node has more elements and move accordingly
			if (successor.count > halfway) {
				temp.addItem(successor.data[0]);
				successor.removeItem(0);
			} else if (successor.count <= halfway) {
				for (int i = 0; i < successor.count; i++) {
					temp.addItem(successor.data[i]);
				}
				// unlinking empty node
				temp.next = successor.next;
				successor.next.previous = temp;
				successor = null;
			}
		}
		// list size goes down since we removed an element
		size--;
		return nodeValue;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		// create new data List for sorting
		E[] sortableDataList = (E[]) new Comparable[size];

		//copy data into a sortable array of type E
		int index = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				sortableDataList[index] = temp.data[i];
				index++;
			}
			temp = temp.next;
		}
		// clean up list for adding
		head.next = tail;
		tail.previous = head;

		// call insertion sort
		insertionSort(sortableDataList, new ItemComparator());
		// add sorted data back into nodes
		size = 0;
		for (int i = 0; i < sortableDataList.length; i++) {
			add(sortableDataList[i]);
		}
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		// create new data List for sorting
		E[] sortableDataList = (E[]) new Comparable[size];

		//get an array of type E to be sorted
		int index = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				sortableDataList[index] = temp.data[i];
				index++;
			}
			temp = temp.next;
		}
		
		// clean up list for adding
		head.next = tail;
		tail.previous = head;

		// call bubble sort
		bubbleSort(sortableDataList);
		// add sorted list back into nodes
		size = 0;
		for (int i = 0; i < sortableDataList.length; i++) {
			add(sortableDataList[i]);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
			// useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	private class StoutListIterator implements ListIterator<E> {

		// directions for remove and set
		private static final int BEHIND = -1;
		private static final int AHEAD = 1;
		private static final int NONE = 0;

		/**
		 * position within the list
		 */
		private int currentPos;

		/**
		 * StoutList for iterator in array form
		 */
		public E[] iteratorArray;

		/**
		 * the last direction gone by the program
		 */
		private int direction;

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			currentPos = 0;
			direction = 0;
			toArray();
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			currentPos = pos;
			direction = 0;
			toArray();
		}

		/**
		 * takes the entire StoutList and puts its data into the dataList an array form
		 */
		private void toArray() {
			iteratorArray = (E[]) new Comparable[size];

			int copyIndex = 0;
			Node copyNode = head.next;
			while (copyNode != tail) {
				for (int i = 0; i < copyNode.count; i++) {
					iteratorArray[copyIndex] = copyNode.data[i];
					copyIndex++;
				}
				copyNode = copyNode.next;
			}
		}

		@Override
		public boolean hasNext() {
			if (currentPos >= size)
				return false;
			else
				return true;
		}

		@Override
		public E next() {
			if (hasNext() == false)
				throw new NoSuchElementException();
			direction = AHEAD;
			return iteratorArray[currentPos++];
		}

		@Override
		public void remove() {
			if (direction == AHEAD) {
				StoutList.this.remove(currentPos - 1);
				// update list of items
				toArray();
				direction = BEHIND;
				currentPos--;
				// if that was the first item in the list
				if (currentPos < 0) {
					currentPos = 0;
				}
			} else if (direction == BEHIND) {
				StoutList.this.remove(currentPos);
				toArray();
				direction = BEHIND;
			} else {
				throw new IllegalStateException();
			}
		}

		@Override
		public boolean hasPrevious() {
			if (currentPos <= 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E previous() {
			if (hasPrevious() == false)
				throw new NoSuchElementException();

			direction = BEHIND;
			currentPos--;
			return iteratorArray[currentPos];
		}

		@Override
		public int nextIndex() {
			if (currentPos == size) {
				return size;
			}
			return currentPos;
		}

		@Override
		public int previousIndex() {
			if (currentPos == 0) {
				return -1;
			}
			return currentPos - 1;
		}

		@Override
		public void set(E e) {
			if (direction == AHEAD) {
				int adjustedPos = currentPos - 1;
				NodeInfo nodeInfo = find(adjustedPos);
				// change node in data of list and our own personal array
				nodeInfo.node.data[nodeInfo.offset] = e;
				iteratorArray[adjustedPos] = e;
			} else if (direction == BEHIND) {
				NodeInfo nodeInfo = find(currentPos);
				// change node in data of list and our own personal array
				nodeInfo.node.data[nodeInfo.offset] = e;
				iteratorArray[currentPos] = e;
			} else {
				throw new IllegalStateException();
			}
		}

		@Override
		public void add(E e) {
			// throw exception if item is null
			if (e == null) {
				throw new NullPointerException();
			}
			// add and clean up
			StoutList.this.add(currentPos, e);
			currentPos++;
			// update node array
			toArray();
			direction = -1;
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	/**
	 * helper class to represent a point on the list
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * locates a specific item in thelist
	 * 
	 * @param pos
	 * @return NodeInfo of the specific point
	 */
	private NodeInfo find(int pos) {
		Node temp = head.next;
		int currentPos = 0;
		// search for node, go back through while if node isn't found
		while (temp != tail) {
			if (currentPos + temp.count <= pos) {
				currentPos += temp.count;
				temp = temp.next;
				continue;
			}

			NodeInfo nodeInfo = new NodeInfo(temp, pos - currentPos);
			return nodeInfo;
		}
		return null;
	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E key = arr[i];
			int j = i - 1;
			while (j >= 0 && comp.compare(arr[j], key) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		int k = arr.length;
		for (int i = 0; i < k - 1; i++) {
			for (int j = 0; j < k - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

	class ItemComparator<E extends Comparable<E>> implements Comparator<E> {
		@Override
		public int compare(E e1, E e2) {
			return e1.compareTo(e2);
		}
	}
}