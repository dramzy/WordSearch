package com.dramzy.wordsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.dramzy.puzzle.Direction;
import com.dramzy.puzzle.Grid;
import com.dramzy.puzzle.GridPuzzle;

public class GridPuzzleParser {
	private InputStream puzzleStream;

	public GridPuzzleParser(final InputStream dictStream) {
		this.puzzleStream = dictStream;
	}

	public GridPuzzle parse() throws ParserException {
		final List<List<Character>> puzzle = new ArrayList<>();
		try (final Scanner inputScanner = new Scanner(puzzleStream)) {
			while (inputScanner.hasNextLine()) {
				String nextLine = inputScanner.nextLine();
				List<String> tokens = Arrays.asList(nextLine.trim().split("\\s+"));
				if (tokens.stream().anyMatch(str -> str.length() != 1)) {
					throw new ParserException("Invalid puzzle format, each must be a single character!");
				}
				puzzle.add(tokens.stream().map(i -> i.toLowerCase().charAt(0)).collect(Collectors.toList()));
			}
		}
		GridPuzzle gridPuzzle = new GridPuzzle(new Grid(puzzle), Direction.getAlldirections(), 3);
		return gridPuzzle;
	}

}
