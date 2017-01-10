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
   private final int width, height;
   private final EnumSet<MatchDirection> matchDirections;

   /**
    * Represents matching directions in the Puzzle
    */
   public enum MatchDirection {
      Horizontal(0, 1), Vertical(1, 0), Diagonal(1, 1), HorizontalBackwards(0, -1), VerticalBAckwards(-1,
            0), DiagonalBackwards(-1, -1);

      private final int xDelta, yDelta;

      MatchDirection(final int xDelta, final int yDelta) {
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

   private GridPuzzle(final char[][] grid, final EnumSet<MatchDirection> matchDirections) {
      this.grid = grid;
      this.matchDirections = matchDirections;
      this.height = grid.length;
      if (height == 0) {
         width = 0;
      } else {
         width = grid[0].length;
      }
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
         return (y < height - 1) || (x < width - 1) || directionIterator.hasNext();
      }

      @Override
      public Path next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         final MatchDirection direction;
         if (directionIterator.hasNext()) {
            direction = directionIterator.next();
         } else {
            directionIterator = matchDirections.iterator();
            direction = directionIterator.next();
            if (x == width) {
               y++;
               x = 0;
            } else {
               x++;
            }
         }
         int xCurr = 0, yCurr = 0;
         final StringBuilder sBuilder = new StringBuilder();
         while (withinBounds(xCurr, yCurr)) {
            sBuilder.append(grid[yCurr][xCurr]);
            xCurr += direction.getxDelta();
            yCurr += direction.getyDelta();
         }
         return new GridPath(new Coords(x, y), new Coords(xCurr, yCurr), sBuilder.toString());
      }

      private boolean withinBounds(final int x, final int y) {
         return x >= 0 && x < width && y >= 0 && y < height;
      }
   }
}