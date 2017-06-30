package com.dramzy.wordsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.dramzy.dictionary.Dictionary;
import com.dramzy.dictionary.TrieDictionary;

public class DictionaryParser {
	private InputStream dictStream;

	public DictionaryParser(final InputStream dictStream) {
		this.dictStream = dictStream;
	}

	public Dictionary parse() {
		Scanner inputScanner = new Scanner(dictStream);
		final ArrayList<String> dict = new ArrayList<>();
		while (inputScanner.hasNextLine()) {
			dict.add(inputScanner.nextLine());
		}
		return new TrieDictionary(dict);
	}

}
