package puzzle;

public interface Puzzle<T> extends Iterable<Path<T>> {

   /**
    * Retrieves the minimum length for a word match in this Puzzle
    * 
    * @return The minimum length for a word match
    */
   public int getMinWordLength();
}
