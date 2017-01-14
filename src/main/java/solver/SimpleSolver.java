package solver;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dictionary.Dictionary;
import puzzle.Path;
import puzzle.Puzzle;

/**
 * A simple implementation of Solver that filters Puzzle Paths that contain
 * Dictionary words
 */
public class SimpleSolver implements Solver {

   private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSolver.class);

   /**
    * {@inheritDoc}
    */
   @Override
   public <T> List<Path<T>> solve(final Dictionary dictionary, final Puzzle<T> puzzle) {
      final int minMatchLength = puzzle.getMinWordLength();
      List<Path<T>> resultSet = new ArrayList<>();
      for (Path<T> path : puzzle) {
         List<String> words = dictionary.findAllWords(path.getWord(), minMatchLength);
         for (String word : words) {
            resultSet.add(path.getSubPath(0, word.length()));
         }
      }
      return resultSet;
   }

}
