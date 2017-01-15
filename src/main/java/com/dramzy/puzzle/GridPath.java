package com.dramzy.puzzle;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a Path in a rectangular Puzzle
 */
public class GridPath implements Path<Coord2d> {

   // The start and end coordinates
   private final Coord2d start, end;
   // The word along this path
   private final String word;
   // The direction of this path
   private final Direction direction;

   public GridPath(final Coord2d start, final Coord2d end, final String word, final Direction direction) {
      this.start = start;
      this.end = end;
      this.word = word;
      this.direction = direction;

   }

   public Coord2d getStart() {
      return start;
   }

   public Coord2d getEnd() {
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
   public Path<Coord2d> getSubPath(final int beginIndex, final int endIndex) {
      if (beginIndex >= word.length()) {
         throw new IndexOutOfBoundsException("The beginning index (" + beginIndex + ") is out of bounds");
      } else if (endIndex > word.length()) {
         throw new IndexOutOfBoundsException("The end index (" + endIndex + ") is out of bounds");
      }
      return new GridPath(start.clone().move(direction, beginIndex), start.clone().move(direction, endIndex - 1),
            word.substring(beginIndex, endIndex), direction);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getWord() {
      return word;
   }

   @Override
   public String toString() {
      return start + " to " + end + ": " + word;
   };

   @Override
   public Iterator<Coord2d> iterator() {
      return new GridPathIterator();
   }

   // Iterates over all coordinates along this path
   private final class GridPathIterator implements Iterator<Coord2d> {
      int index = 0;

      @Override
      public boolean hasNext() {
         return (index < word.length());
      }

      @Override
      public Coord2d next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         return start.clone().move(direction, index);
      }

   }
}
