package A2Q1;

import java.util.*;

/**
 * Represents a sorted integer array. Provides a method, kpairSum, that
 * determines whether the array contains two elements that sum to a given
 * integer k. Runs in O(n) time, where n is the length of the array.
 * 
 * @author jameselder
 */
public class SortedIntegerArray {

	protected int[] sortedIntegerArray;

	public SortedIntegerArray(int[] integerArray) {
		sortedIntegerArray = integerArray.clone();
		Arrays.sort(sortedIntegerArray);
	}

	/**
	 * Determines whether the array contains two elements that sum to a given
	 * integer k. Runs in O(n) time, where n is the length of the array.
	 * 
	 * @author jameselder
	 */
	public boolean kPairSum(Integer k) {
		if (sortedIntegerArray.length <= 1) {
			return false;
		}
		return kPairSumInterval(k, 0, sortedIntegerArray.length - 1);
	}

	public boolean kPairSumInterval(Integer k, int i, int j) {
		long sumij = (long) sortedIntegerArray[i] + (long) sortedIntegerArray[j];
		if (i == j) {
			return false;
		}
		if (k == sumij) {
			return true;
		}
		if (k > sumij) {
			return kPairSumInterval(k, i + 1, j);
		}
		if (k < sumij) {
			return kPairSumInterval(k, i, j - 1);
		}
		return false;
	}

}