package com.dramzy.wordsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PuzzleParser {
	private InputStream puzzleStream;

	public PuzzleParser(final InputStream dictStream) {
		this.puzzleStream = dictStream;
	}

	public List<String> parse() {
		final ArrayList<String> puzzle = new ArrayList<>();
		try (final Scanner inputScanner = new Scanner(puzzleStream)) {
			while (inputScanner.hasNextLine()) {
				puzzle.add(inputScanner.nextLine());
			}
		}
		return puzzle;
	}

}
