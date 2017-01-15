package puzzle;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a rectangular grid
 */
public class Grid implements Iterable<Coord2d> {
   // The grid's contents
   private final char[][] grid;
   // The height and width of this Grid
   private final int width, height;

   /**
    * Constructs a Grid from a rectangular 2d array
    * 
    * @param grid
    *           The Grid contents, must be a rectangular 2d array
    * 
    * @throws IllegalArgumentException
    *            If grid is empty or not rectangular
    */
   public Grid(final char[][] grid) {
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
      if (!isRectangular()) {
         throw new IllegalArgumentException(
               "Grid must be rectangular! Received a grid with height = " + height + " and width = " + width);
      }
   }

   /*
    * Determines if this Grid is rectangular
    */
   private boolean isRectangular() {
      for (final char[] array : grid) {
         if (array.length != width) {
            return false;
         }
      }
      return true;
   }

   /**
    * Retrieves the width of the Grid
    * 
    * @return The width of the Grid
    */
   public final int getWidth() {
      return width;
   }

   /**
    * Retrieves the height of the Grid
    * 
    * @return The height of the Grid
    */
   public final int getHeight() {
      return height;
   }

   /**
    * Determines if the given coords lie within the Grid
    * 
    * @param coords
    *           The coordinates to check for
    * @return true if the Grid contains the given coords
    */
   public boolean contains(final Coord2d coords) {
      return coords.getX() >= 0 && coords.getX() < width && coords.getY() >= 0 && coords.getY() < height;
   }

   /**
    * Retrieves the element at the given coords within the Grid
    * 
    * @param coords
    *           The coordinates to retrieve the element from
    * @return The element at coords
    * @throws IndexOutOfBoundsException
    *            if coords lies outside of the Grid
    */
   public char getElementAt(final Coord2d coords) {
      if (!contains(coords)) {
         throw new IndexOutOfBoundsException("Coordinates " + coords + " lie outside of the Grid.");
      }
      return grid[coords.getY()][coords.getX()];
   }

   /**
    * Get the longest possible path along direction starting at startCoords
    * 
    * @param startCoords
    *           The starting coordinates
    * @param direction
    *           The direction of the Path
    * @return The longest Path along direction starting from startCoords
    * 
    * @throws IllegalArgumentException
    *            if the grid does not contain the starting coordinates
    * @throws NullPointerException
    *            if direction is null
    */
   public Path<Coord2d> getPath(final Coord2d startCoords, final Direction direction) {
      if (!contains(startCoords)) {
         throw new IllegalArgumentException("Grid does not contain these coordinates: " + startCoords);
      }
      Coord2d currCoords = startCoords.clone();
      final StringBuilder sBuilder = new StringBuilder();
      while (!reachedEndOfPath(currCoords, direction)) {
         sBuilder.append(getElementAt(currCoords));
         currCoords.move(direction, 1);
      }
      return new GridPath(startCoords, currCoords, sBuilder.toString(), direction);
   }

   private boolean reachedEndOfPath(final Coord2d coords, final Direction direction) {
      return !contains(coords.clone().move(direction, 1));
   }

   @Override
   public Iterator<Coord2d> iterator() {
      return new GridIterator();
   }

   private final class GridIterator implements Iterator<Coord2d> {
      private final Coord2d coords = new Coord2d(0, 0);

      @Override
      public boolean hasNext() {
         return !(coords.getX() == width - 1 && coords.getY() == height - 1);
      }

      @Override
      public Coord2d next() {
         if (!hasNext()) {
            throw new NoSuchElementException("No more elements in Grid");
         }
         if (coords.getX() == getWidth() - 1) {
            coords.moveY(1).setX(0);
         } else {
            coords.moveX(1);
         }
         return coords.clone();
      }

   }
}
