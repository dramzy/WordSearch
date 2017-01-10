package wordsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of Solver that filters Puzzle Paths that contain
 * Dictionary words
 */
public class SimpleSolver implements Solver {

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Path> solve(final Dictionary dictionary, final Puzzle puzzle) {
      List<Path> resultSet = new ArrayList<>();
      for (Path path : puzzle) {
         List<String> words = dictionary.findAllWords(path.getWord(), 3);
         for (String word : words) {
            resultSet.add(path.getSubPath(0, word.length()));
         }
      }
      return resultSet;
   }

}
