package com.dramzy.puzzle;

/**
 * Represents a path in a word search puzzle
 */
public interface Path<T> extends Iterable<T> {
   /**
    * Returns a Path that is a subPath of this Path starting at beginIndex and
    * ending at endIndex
    * 
    * @param beginIndex
    *           the beginning index, inclusive
    * @param endIndex
    *           the ending index, exclusive
    * @return The sub-Path starting from beginIndex and ending at endIndex
    * @throws IndexOutOfBoundsException
    *            if beginIndex or endIndex are out of bounds
    */
   public Path<T> getSubPath(final int beginIndex, final int endIndex);

   /**
    * Get the String along this path
    * 
    * @return The String contained within this Path
    */
   public String getWord();

   /**
    * Retrieves the length of this Path
    * 
    * @return The length of this Path
    */
   public int getLength();

}
