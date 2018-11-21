package A3Q1;

/**
 * Extends the TreeMap class to allow convenient access to entries within a
 * specified range of key values (findAllInRange).
 * 
 * @author jameselder
 */
public class BSTRange<K, V> extends TreeMap<K, V> {

	/*
	 * Returns the lowest (deepest) position in the subtree rooted at pos that is a
	 * common ancestor to positions with keys k1 and k2, or to the positions they
	 * would occupy were they present.
	 */
	protected Position<Entry<K, V>> findLowestCommonAncestor(K k1, K k2, Position<Entry<K, V>> pos) {
		if (isInternal(pos)) {
			if (compare(k1, pos.getElement().getKey()) > 0 && compare(k2, pos.getElement().getKey()) > 0) {
				return findLowestCommonAncestor(k1, k2, right(pos));
			}
			if (compare(k1, pos.getElement().getKey()) < 0 && compare(k2, pos.getElement().getKey()) < 0) {
				return findLowestCommonAncestor(k1, k2, left(pos));
			}
		}
		return pos;
	}

	/*
	 * Finds all entries in the subtree rooted at pos with keys of k or greater and
	 * copies them to L, in non-decreasing order.
	 */
	protected void findAllAbove(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
		if (isInternal(left(pos))) {
			findAllAbove(k, left(pos), L);
		}
		if (compare(pos.getElement().getKey(), k) >= 0) {
			L.addLast(pos.getElement());
		}

		if (isInternal(right(pos))) {
			findAllAbove(k, right(pos), L);
		}
	}

	/*
	 * Finds all entries in the subtree rooted at pos with keys of k or less and
	 * copies them to L, in non-decreasing order.
	 */
	protected void findAllBelow(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
		if (isInternal(left(pos))) {
			findAllBelow(k, left(pos), L);
		}
		if (compare(pos.getElement().getKey(), k) <= 0) {
			L.addLast(pos.getElement());
		}

		if (isInternal(right(pos))) {
			findAllBelow(k, right(pos), L);
		}
	}

	/*
	 * Returns all entries with keys no less than k1 and no greater than k2, in
	 * non-decreasing order.
	 */
	public PositionalList<Entry<K, V>> findAllInRange(K k1, K k2) {
		PositionalList<Entry<K, V>> L = new LinkedPositionalList<>();
		Position<Entry<K, V>> pos = tree.root();
		Position<Entry<K, V>> ancestor = findLowestCommonAncestor(k1, k2, pos);
		Position<Entry<K, V>> abovePos = left(ancestor);
		Position<Entry<K, V>> belowPos = right(ancestor);

		if (compare(k2, k1) >= 0 && isInternal(ancestor) && compare(ancestor.getElement().getKey(), k1) >= 0
				&& compare(ancestor.getElement().getKey(), k2) <= 0) {

			if (isInternal(abovePos)) {
				findAllAbove(k1, abovePos, L);
			}

			L.addLast(ancestor.getElement());

			if (isInternal(belowPos)) {
				findAllBelow(k2, belowPos, L);
			}
		}
		return L;
	}
}
