package com.dramzy.wordsearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.dramzy.dictionary.Dictionary;
import com.dramzy.puzzle.Coord2d;
import com.dramzy.puzzle.GridPuzzle;
import com.dramzy.puzzle.Path;
import com.dramzy.solver.SimpleSolver;
import com.dramzy.solver.Solver;

public class WordSearch {
	private static String appName = "WordSearch", default_dictionary_resource = "/com/dramzy/dict";

	public static void main(final String[] argv) {
		InputStream puzzleStream = System.in,
				dictionaryStream = WordSearch.class.getResourceAsStream(default_dictionary_resource);
		ArgParser parser = new ArgParser();
		try {
			parser.parse(argv);
			Optional<String> puzzleFile = parser.getPuzzleFilePath(), dictFile = parser.getDictFilePath();
			puzzleStream = puzzleFile.isPresent() ? new FileInputStream(puzzleFile.get()) : puzzleStream;
			dictionaryStream = dictFile.isPresent() ? new FileInputStream(dictFile.get()) : dictionaryStream;
			GridPuzzle puzzle = (new GridPuzzleParser(puzzleStream)).parse();
			puzzle.setMinWordLength(parser.getMatchLength());
			Dictionary dictionary = (new DictionaryParser(dictionaryStream)).parse();
			Solver solver = new SimpleSolver();
			List<Path<Coord2d>> matches = solver.solve(dictionary, puzzle);
			matches.stream().forEach(match -> System.out.println(match));
		} catch (ArgParser.UsageException ue) {
			fail(1, "Usage error: " + ue.getMessage());
		} catch (FileNotFoundException fe) {
			fail(2, "Error reading file: " + fe.getMessage());
		} catch (ParserException pe) {
			fail(3, "Parsing error: " + pe.getMessage());
		} catch (Exception e) {
			fail(4, "Error: " + e.getMessage());
		}
	}

	private static void fail(final int errorCode, final String... messages) {
		Arrays.asList(messages).stream().forEach(message -> System.err.println(message));
		System.exit(errorCode);
	}

	private static class ArgParser {
		// TODO: add support for passing in match directions
		private Optional<String> puzzleFilePath = Optional.empty(), dictFilePath = Optional.empty();
		private int matchLength = 3;
		private final String usage = "Usage: " + appName + " [-d|--dictionary <file>] [-p|--puzzle <file>]";

		public Optional<String> getPuzzleFilePath() {
			return puzzleFilePath;
		}

		public Optional<String> getDictFilePath() {
			return dictFilePath;
		}

		public int getMatchLength() {
			return matchLength;
		}

		void parse(final String[] argv) throws UsageException {
			Iterator<String> it = Arrays.asList(argv).iterator();
			while (it.hasNext()) {
				switch (it.next()) {
				case "-d":
				case "--dictionary":
					if (!it.hasNext()) {
						throw new UsageException(argv, "Dictionary file path expected");
					}
					dictFilePath = Optional.of(it.next());
					break;
				case "-p":
				case "--puzzle":
					if (!it.hasNext()) {
						throw new UsageException(argv, "Puzzle file path expected");
					}
					puzzleFilePath = Optional.of(it.next());
					break;
				case "-l":
				case "--match-length":
					try {
						matchLength = Integer.parseUnsignedInt(it.next());
					} catch (NumberFormatException | NoSuchElementException e) {
						throw new UsageException(argv, "Numeric minimum match length expected");
					}
					break;
				default:
					throw new UsageException(argv, "Unexpected argument");
				}
			}
		}

		public static class UsageException extends Exception {
			public UsageException(final String[] argv, final String... msgs) {
				super(String.join("\n", msgs) + "\narguments: " + String.join(" ", argv));
			}
		}

	}

}
