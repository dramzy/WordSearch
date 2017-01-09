package wordsearch;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a rectangular word search puzzle
 *
 */
public class GridPuzzle implements Puzzle {
   private final char[][] grid;
   private final EnumSet<MatchDirection> matchDirections;

   /**
    * Represents matching directions in the Puzzle
    */
   public enum MatchDirection {
      Horizontal(0, 1), Vertical(1, 0), Diagonal(1, 1), HorizontalBackwards(0, -1), VerticalBAckwards(-1,
            0), DiagonalBackwards(-1, -1);

      final int xDelta, yDelta;

      MatchDirection(final int xDelta, final int yDelta) {
         this.xDelta = xDelta;
         this.yDelta = yDelta;
      }
   }

   private GridPuzzle(final char[][] grid, final EnumSet<MatchDirection> matchDirections) {
      this.grid = grid;
      this.matchDirections = matchDirections;
   }

   @Override
   public Iterator<Path> iterator() {
      return new GridPuzzleIterator();
   }

   /*
    * Iterates over all Paths inside this Puzzle
    */
   private final class GridPuzzleIterator implements Iterator<Path> {
      private int x = 0, y = 0;
      private Iterator<MatchDirection> directionIterator = matchDirections.iterator();

      @Override
      public boolean hasNext() {
         if (grid.length == 0) {
            return false;
         }
         return (x < grid[0].length) || (y < grid.length) || directionIterator.hasNext();
      }

      @Override
      public Path next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         // TODO: implement next()
         return null;
      }

   }
}
