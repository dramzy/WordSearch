package com.dramzy.wordsearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

public class WordSearch {
	private static String appName = "WordSearch";
	private static String default_dictionary_resource = "/com/dramzy/dict";

	public static void main(final String[] argv) {
		ArgParser parser = new ArgParser();
		parser.parse(argv);
		InputStream dictStream = null, puzzleStream = null;
		if (parser.getPuzzleFilePath() == null) {
			puzzleStream = System.in;
		} else {
			try (final FileInputStream puzzleFileStream = new FileInputStream(parser.getDictFilePath())) {
				puzzleStream = puzzleFileStream;
			} catch (IOException ioe) {
				System.err.println("Error reading puzzle file: " + parser.getDictFilePath());
				System.exit(1);
			}
		}

		if (parser.getDictFilePath() == null) {
			dictStream = WordSearch.class.getResourceAsStream(default_dictionary_resource);
		} else {
			try (final FileInputStream dictFileStream = new FileInputStream(parser.getDictFilePath())) {
				dictStream = dictFileStream;
			} catch (IOException ioe) {
				System.err.println("Error reading dictionary file: " + parser.getDictFilePath());
				System.exit(1);
			}
		}

		final PuzzleParser puzzleParser = new PuzzleParser(puzzleStream);
		final DictionaryParser dictParser = new DictionaryParser(dictStream);

		// TODO: parse dictionary and puzzle, and display matches
	}

	private static class ArgParser {
		private String puzzleFilePath, dictFilePath;
		private final String usage = "Usage: " + appName + " [-d|--dictionary <file>] [-p|--puzzle <file>]";

		public String getPuzzleFilePath() {
			return puzzleFilePath;
		}

		public String getDictFilePath() {
			return dictFilePath;
		}

		void parse(final String[] argv) {
			Iterator<String> it = Arrays.asList(argv).iterator();
			while (it.hasNext()) {
				switch (it.next()) {
				case "-d":
				case "--dictionary":
					if (!it.hasNext()) {
						fail("Dictionary file path expected", argv);
					}
					dictFilePath = it.next();
					break;
				case "-p":
				case "--puzzle":
					if (!it.hasNext()) {
						fail("Puzzle file path expected", argv);
					}
					puzzleFilePath = it.next();
					break;
				default:
					fail("Unexpected argument", argv);
				}
			}
		}

		void fail(final String message, final String[] argv) {
			System.err.println("ERROR: " + message);
			System.err.println("arguments: " + argv);
			System.err.println(usage);
			System.exit(1);
		}

	}

}
