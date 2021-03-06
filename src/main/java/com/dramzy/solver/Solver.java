package com.dramzy.solver;

import java.util.List;

import com.dramzy.dictionary.Dictionary;
import com.dramzy.puzzle.Path;
import com.dramzy.puzzle.Puzzle;

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
   public <T> List<Path<T>> solve(final Dictionary dictionary, final Puzzle<T> puzzle);
}
