package puzzle;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a rectangular word search puzzle
 *
 */
public class GridPuzzle implements Puzzle<Coord2d> {
   private final char[][] grid;
   private final int width, height, minWordLength;
   private final EnumSet<Direction> matchDirections;

   public GridPuzzle(final char[][] grid, final EnumSet<Direction> matchDirections, final int minWordLength) {
      this.height = grid.length;
      if (height == 0) {
         width = 0;
      } else {
         width = grid[0].length;
      }
      if (height == 0 || width == 0) {
         throw new IllegalArgumentException(
               "Grid cannot be empty! Received a grid with height = " + height + " and width = " + width);
      }
      this.grid = grid;
      this.matchDirections = matchDirections;
      if (matchDirections.size() == 0) {
         throw new IllegalArgumentException("Match directions cannot be empty!");
      }
      this.minWordLength = minWordLength;
      if (minWordLength < 1) {
         throw new IllegalArgumentException("The minimum word length has to be larger than 0");
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getMinWordLength() {
      return minWordLength;
   }

   private boolean contains(final Coord2d coords) {
      return coords.getX() >= 0 && coords.getX() < width && coords.getY() >= 0 && coords.getY() < height;
   }

   private char get(final Coord2d coords) {
      return grid[coords.getY()][coords.getX()];
   }

   @Override
   public Iterator<Path<Coord2d>> iterator() {
      return new GridPuzzleIterator();
   }

   /*
    * Iterates over all Paths inside this Puzzle
    */
   private final class GridPuzzleIterator implements Iterator<Path<Coord2d>> {
      private Coord2d coords = new Coord2d(0, 0);
      private Iterator<Direction> directionIterator = matchDirections.iterator();
      private Direction matchDirection = directionIterator.next();

      @Override
      public boolean hasNext() {
         while (hasMorePathsToTry() && !contains(coords.clone().move(matchDirection, minWordLength))) {
            moveToNextPath();
         }
         return hasMorePathsToTry();
      }

      private boolean hasMorePathsToTry() {
         return (coords.getY() < height - 1) || (coords.getX() < width - 1) || directionIterator.hasNext();
      }

      @Override
      public Path<Coord2d> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Coord2d currCoords = coords.clone();
         final StringBuilder sBuilder = new StringBuilder();
         while (!reachedEndOfPath(currCoords)) {
            sBuilder.append(get(currCoords));
            currCoords.move(matchDirection, 1);
         }
         GridPath path = new GridPath(coords.clone(), currCoords, sBuilder.toString(), matchDirection);
         moveToNextPath();
         return path;
      }

      private void moveToNextPath() {
         if (directionIterator.hasNext()) {
            matchDirection = directionIterator.next();
         } else {
            directionIterator = matchDirections.iterator();
            matchDirection = directionIterator.next();
            if (coords.getX() == width - 1) {
               coords.moveY(1).setX(0);
            } else {
               coords.moveX(1);
            }
         }

      }

      private boolean reachedEndOfPath(final Coord2d coords) {
         return !contains(coords.clone().move(matchDirection, 1));
      }

   }
}