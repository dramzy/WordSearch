package wordsearch;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a Path in a rectangular Puzzle
 *
 */
public class GridPath implements Path {

   // The start and end coordinates
   private final Coords start, end;
   // The word along this path
   private final String word;
   // The change in x and y coordinates along this path
   private final int xDelta, yDelta;

   public GridPath(final Coords start, final Coords end, final String word) {
      this.start = start;
      this.end = end;
      this.word = word;
      this.xDelta = Integer.signum(end.getX() - start.getX());
      this.yDelta = Integer.signum(end.getY() - start.getY());

   }

   public Coords getStart() {
      return start;
   }

   public Coords getEnd() {
      return end;
   }

   @Override
   public int getLength() {
      return word.length();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Path getSubPath(final int beginIndex, final int endIndex) {
      if (beginIndex >= word.length()) {
         throw new IndexOutOfBoundsException("The beginning index (" + beginIndex + ") is out of bounds");
      } else if (endIndex > word.length()) {
         throw new IndexOutOfBoundsException("The end index (" + endIndex + ") is out of bounds");
      }
      return new GridPath(new Coords(xDelta * beginIndex + start.getX(), yDelta * beginIndex + start.getY()),
            new Coords(xDelta * (endIndex - 1) + start.getX(), yDelta * (endIndex - 1) + start.getY()),
            word.substring(beginIndex, endIndex));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getWord() {
      return word;
   }

   @Override
   public Iterator<Coords> iterator() {
      return new GridPathIterator();
   }

   // Iterates over all coordinates along this path
   private final class GridPathIterator implements Iterator<Coords> {
      int index = 0;

      @Override
      public boolean hasNext() {
         return (index < word.length());
      }

      @Override
      public Coords next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         return new Coords(xDelta * index + start.getX(), yDelta * index + start.getY());
      }

   }
}
