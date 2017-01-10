package wordsearch;

import java.util.List;

/**
 * Represents a Puzzle solver
 */
public interface Solver {

   /**
    * Returns a list of Paths that contain Dictionary words
    * 
    * @param dictionary
    *           The dictionary to look up words in
    * @param puzzle
    *           The Puzzle to solve
    * @return A list of Paths that contain Dictionary words
    */
   public List<Path> solve(final Dictionary dictionary, final Puzzle puzzle);
}
