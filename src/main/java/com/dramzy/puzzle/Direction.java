package com.dramzy.puzzle;

import java.util.EnumSet;

/**
 * Represents matching directions in the Puzzle
 */
public enum Direction {
	Horizontal(1, 0), Vertical(0, 1), Diagonal(1, 1), HorizontalBackwards(-1, 0), VerticalBackwards(0,
			-1), DiagonalBackwards(-1, -1);

	private final int xDelta, yDelta;

	public final static EnumSet<Direction> getAlldirections() {
		return EnumSet.allOf(Direction.class);
	}

	Direction(final int xDelta, final int yDelta) {
		this.xDelta = xDelta;
		this.yDelta = yDelta;
	}

	public int getxDelta() {
		return xDelta;
	}

	public int getyDelta() {
		return yDelta;
	}

}