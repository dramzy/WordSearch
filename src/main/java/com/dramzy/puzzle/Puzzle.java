package com.dramzy.puzzle;

public interface Puzzle<T> extends Iterable<Path<T>> {

	/**
	 * Retrieves the minimum length for a word match in this Puzzle
	 * 
	 * @return The minimum length for a word match
	 */
	public int getMinWordLength();

	/**
	 * Sets the minimum length for a word match in this Puzzle
	 * 
	 * @param minWordLength
	 *            The minimum length for a word match
	 */
	public void setMinWordLength(final int minWordLength);
}
