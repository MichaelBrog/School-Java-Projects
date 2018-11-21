package A1Q3;

import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides a
 * method for determining the maximum element on the stack in O(1) time.
 * 
 * @author jameselder
 */
public class MaxStack<E extends Comparable> extends Stack<E> {

	private Stack<E> maxStack;
	private Stack<E> originalStack;

	public MaxStack() {
		maxStack = new Stack<>();
		originalStack = new Stack<>();
	}

	/* must run in O(1) time */
	public E push(E element) {
		if (maxStack.size() == 0) {
			maxStack.push(element);
		} else if (maxStack.peek().compareTo(element) <= 0) {
			maxStack.push(element);
		}
		originalStack.push(element);
		return element;
	}

	/* @exception EmptyStackException if this stack is empty. */
	/* must run in O(1) time */
	public synchronized E pop() {
		if (maxStack.size() == 0) {
			throw new EmptyStackException();
		}
		if (originalStack.peek().compareTo(maxStack.peek()) == 0) {
			maxStack.pop();
		}
		return originalStack.pop();
	}

	/* Returns the maximum value currenctly on the stack. */
	/* @exception EmptyStackException if this stack is empty. */
	/* must run in O(1) time */
	public synchronized E max() {
		if (maxStack.size() == 0) {
			throw new EmptyStackException();
		}
		return maxStack.peek();
	}

}