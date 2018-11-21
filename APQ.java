package A2Q2;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array. The order in which equal entries were added is
 * preserved.
 *
 * @author jameselder
 * @param <E>
 *            The entry type.
 */
public class APQ<E> {

	private final ArrayList<E> apq; // will store the min heap
	private final Comparator<E> comparator; // to compare the entries
	private final Locator<E> locator; // to locate the entries within the queue

	/**
	 * Constructor
	 * 
	 * @param comparator
	 *            used to compare the entries
	 * @param locator
	 *            used to locate the entries in the queue
	 * @throws NullPointerException
	 *             if comparator or locator parameters are null
	 */
	
	/*
	 * Work done by Michael Brog
	 * Chapter 9 of course textbook was used as reference material 
	 */
	public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
		if (comparator == null || locator == null) {
			throw new NullPointerException();
		}
		apq = new ArrayList<>();
		apq.add(null); // dummy value at index = 0
		this.comparator = comparator;
		this.locator = locator;
	}

	public static int parent(int n) {
		return (int) ((n - 1) / 2);
	}

	public static int leftChild(int i) {
		return 2 * i + 1;
	}

	public static int rightChild(int i) {
		return 2 * i + 2;
	}

	/**
	 * Inserts the specified entry into this priority queue.
	 *
	 * @param e
	 *            the entry to insert
	 * @throws NullPointerException
	 *             if parameter e is null
	 */
	public void offer(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException();
		}
		apq.add(e);
		upheap(this.size()); // size changed
		// implement this method
	}

	/**
	 * Removes the entry at the specified location.
	 *
	 * @param pos
	 *            the location of the entry to remove
	 * @throws BoundaryViolationException
	 *             if pos is out of range
	 */
	public void remove(int pos) throws BoundaryViolationException {
		// implement this method
		if (apq.isEmpty())
			return;
		if (pos >= apq.size() || pos < 1) {
			throw new BoundaryViolationException();
		}
		swap(pos, apq.size() - 1); // -1 changed
		apq.remove(apq.size() - 1);// -1 changed
		downheap(pos);
	}

	/**
	 * Removes the first entry in the priority queue.
	 */
	public E poll() {
		if (apq.isEmpty())
			return null;
		E retValue = apq.get(1);
		swap(1, apq.size() - 1); // -1 changed, change back if it doesnt work
		apq.remove(apq.size() - 1); // -1 changed, change back if it doesnt work
		downheap(1);
		return retValue; // implement this method
	}

	/**
	 * Returns but does not remove the first entry in the priority queue.
	 */
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return apq.get(1);
	}

	public boolean isEmpty() {
		return (size() == 0);
	}

	public int size() {
		return apq.size() - 1; // dummy node at location 0
	}

	/**
	 * Shift the entry at pos upward in the heap to restore the minheap property
	 * 
	 * @param pos
	 *            the location of the entry to move
	 */
	private void upheap(int pos) {
		// implement this method
		while (pos > 1) {
			int pos2 = (pos) / 2;
			if (pos < 0)
				pos = 0;
			if ((comparator.compare(apq.get(pos), apq.get(pos2)) >= 0)) {
				break;
			}
			swap(pos, pos2);
			pos = pos2;

		}
		locator.set(apq.get(pos), pos);
	}

	/**
	 * Shift the entry at pos downward in the heap to restore the minheap property
	 * 
	 * @param pos
	 *            the location of the entry to move
	 */
	private void downheap(int pos) {
		while (leftChild(pos) < this.size()) {
			int leftIndex = leftChild(pos);
			int smallChildIndex = leftIndex;
			if (rightChild(pos) < this.size()) {
				int rightIndex = rightChild(pos);
				if ((comparator.compare(apq.get(leftIndex), apq.get(rightIndex)) > 0)) {
					smallChildIndex = rightIndex;
				}
			}
			if (comparator.compare(apq.get(smallChildIndex), apq.get(pos)) >= 0) {
				break;
			}
			swap(pos, smallChildIndex);
			pos = smallChildIndex;
			locator.set(apq.get(pos), pos);
		}

	}

	/**
	 * Swaps the entries at the specified locations.
	 *
	 * @param pos1
	 *            the location of the first entry
	 * @param pos2
	 *            the location of the second entry
	 */
	private void swap(int pos1, int pos2) {
		E temp = apq.get(pos1);
		apq.set(pos1, apq.get(pos2));
		apq.set(pos2, temp);

		locator.set(apq.get(pos1), pos1);
		locator.set(apq.get(pos2), pos2);
	}
}