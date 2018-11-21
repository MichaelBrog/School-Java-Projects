package A1Q1;

import java.util.*;

/**
 * Represents a sparse numeric vector. Elements are comprised of a (long)
 * location index an a (double) value. The vector is maintained in increasing
 * order of location index, which facilitates numeric operations like inner
 * products (projections). Note that location indices can be any integer from 1
 * to Long.MAX_VALUE. The representation is based upon a singly-linked list. The
 * following methods are supported: iterator, getSize, getFirst, add, remove,
 * and dot, which takes the dot product of the with a second vector passed as a
 * parameter.
 * 
 * @author jameselder
 */
public class SparseNumericVector implements Iterable {

	protected SparseNumericNode head = null;
	protected SparseNumericNode tail = null;
	protected long size;

	/**
	 * Iterator
	 */
	@Override
	public Iterator<SparseNumericElement> iterator() { // iterator
		return new SparseNumericIterator(this);
	}

	/**
	 * @return number of non-zero elements in vector
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @return the first node in the list.
	 */
	public SparseNumericNode getFirst() {
		return head;
	}

	/**
	 * Add the element to the vector. It is inserted to maintain the vector in
	 * increasing order of index. If the element has zero value, or if an element
	 * with the same index already exists, an UnsupportedOperationException is
	 * thrown.
	 * 
	 * @param e
	 *            element to add
	 */
	public void add(SparseNumericElement e) throws UnsupportedOperationException {
		if (e.getValue() == 0 || e == null) {
			throw new UnsupportedOperationException();
		}
		if (this.getSize() == 0) {
			this.head = new SparseNumericNode(e, null);
			this.tail = this.head;
			size++;
			return;
		}

		if (this.getSize() == 1) {
			if (e.getIndex() < this.head.getElement().getIndex()) {
				this.tail = this.head;
				this.head = new SparseNumericNode(e, this.tail);
				size++;
				return;
			}
			if (e.getIndex() > this.head.getElement().getIndex()) {
				this.tail = new SparseNumericNode(e, null);
				this.head.setNext(this.tail);
				size++;
				return;
			}
		}

		SparseNumericNode nextNode = this.getFirst();
		SparseNumericNode prevNode = this.getFirst();
		if (e.getIndex() == this.head.getElement().getIndex()) {
			throw new UnsupportedOperationException();
		}
		if (e.getIndex() < this.head.getElement().getIndex()) {
			SparseNumericNode newNode = new SparseNumericNode(e, this.head);
			this.head = newNode;
			size++;
			return;

		}

		while (nextNode.getNext() != null && e.getIndex() > nextNode.getElement().getIndex()) {
			if (e.getIndex() == nextNode.getElement().getIndex()) {
				throw new UnsupportedOperationException();
			}
			prevNode = nextNode;
			nextNode = nextNode.getNext();
		}
		SparseNumericNode newNode = new SparseNumericNode(e, nextNode);
		prevNode.setNext(newNode);
		size++;
	}

	/**
	 * If an element with the specified index exists, it is removed and the method
	 * returns true. If not, it returns false.
	 *
	 * @param index
	 *            of element to remove
	 * @return true if removed, false if does not exist
	 */
	public boolean remove(Long index) {
		// case where the vector is empty
		if (this.head == null) {
			return false;
		}
		// case where the index = head
		if (this.head.getElement().getIndex() == index) {
			this.head = this.head.getNext();
			return true;
		}
		SparseNumericNode prevNode = this.head;
		// while loop that brings us to the node before the desired node
		while (prevNode.getNext() != null && prevNode.getNext().getElement().getIndex() != index) {
			prevNode = prevNode.getNext();
		}
		// if the next node is null, that means we did not find the node because it
		// reached the end of the list
		if (prevNode.getNext() == null) {
			return false;
		}
		// If the next next node is null, that means once we remove the next node, our
		// current node will become the new tail
		if (prevNode.getNext().getNext() == null) {
			prevNode.setNext(null);
			this.tail = prevNode;
			return true;
		}
		// sets the next node
		prevNode.setNext(prevNode.getNext().getNext());
		return true;
	}

	/**
	 * Returns the inner product of the vector with a second vector passed as a
	 * parameter. The vectors are assumed to reside in the same space. Runs in
	 * O(m+n) time, where m and n are the number of non-zero elements in each
	 * vector.
	 * 
	 * @param Y
	 *            Second vector with which to take inner product
	 * @return result of inner prosduct
	 */

	public double dot(SparseNumericVector Y) {
		double dotproduct = 0;
		SparseNumericNode n = this.getFirst();
		SparseNumericNode m = Y.getFirst();

		while (n != null && m != null) {
			if (n.getElement().getIndex() > m.getElement().getIndex()) {
				m = m.getNext();
			} else if (n.getElement().getIndex() == m.getElement().getIndex()) {
				dotproduct += m.getElement().getValue() * n.getElement().getValue();
				n = n.getNext();
			} else {
				n = n.getNext();
			}
		}
		return dotproduct;
	}

	/**
	 * returns string representation of sparse vector
	 */

	@Override
	public String toString() {
		String sparseVectorString = "";
		Iterator<SparseNumericElement> it = iterator();
		SparseNumericElement x;
		while (it.hasNext()) {
			x = it.next();
			sparseVectorString += "(index " + x.getIndex() + ", value " + x.getValue() + ")\n";
		}
		return sparseVectorString;
	}
}
