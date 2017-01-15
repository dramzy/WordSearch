package puzzle;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a rectangular word search puzzle
 *
 */
public class GridPuzzle implements Puzzle<Coord2d> {
   private final static Logger LOGGER = LoggerFactory.getLogger(GridPuzzle.class);
   private final Grid grid;
   private final int minWordLength;
   private final EnumSet<Direction> matchDirections;

   public GridPuzzle(final Grid grid, final EnumSet<Direction> matchDirections, final int minWordLength) {
      this.grid = grid;
      this.matchDirections = matchDirections;
      if (matchDirections.isEmpty()) {
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

   @Override
   public Iterator<Path<Coord2d>> iterator() {
      return new GridPuzzleIterator();
   }

   /*
    * Iterates over all Paths inside this Puzzle
    */
   private final class GridPuzzleIterator implements Iterator<Path<Coord2d>> {
      // The Grid iterator
      private final Iterator<Coord2d> coordsIterator = grid.iterator();
      // The current coordinates
      private Coord2d coords = coordsIterator.next();
      // The current direction iterator; gets reinitialized for every coords
      private Iterator<Direction> directionIterator = matchDirections.iterator();
      // The current direction to find paths along starting from coords
      private Direction direction = directionIterator.next();

      @Override
      public boolean hasNext() {
         // Keep looking for a path that is at least minWordLength long
         while (hasMorePathsToTry() && !grid.contains(coords.clone().move(direction, minWordLength))) {
            moveToNextPath();
         }
         // At this point, we either found a viable path or ran out of paths to
         // try. If there are more paths to try, then we found a viable path.
         return hasMorePathsToTry();
      }

      /*
       * Determines if there are more potential paths to explore
       */
      private boolean hasMorePathsToTry() {
         return coordsIterator.hasNext() || directionIterator.hasNext();
      }

      @Override
      public Path<Coord2d> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         // Construct a GridPath starting at coords in direction matchDirection
         Coord2d currCoords = coords.clone();
         Path<Coord2d> path = grid.getPath(currCoords, direction);
         moveToNextPath();
         return path;
      }

      /*
       * Move on to the next possible path
       */
      private void moveToNextPath() {
         if (directionIterator.hasNext()) {
            // More paths to explore starting from the current coordinates
            direction = directionIterator.next();
         } else {
            // Move on to the next coordinates
            directionIterator = matchDirections.iterator();
            direction = directionIterator.next();
            coords = coordsIterator.next();
         }
      }
   }
}