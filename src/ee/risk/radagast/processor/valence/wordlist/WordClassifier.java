/*
 *     Radagast is a classification framework for user submitted textual content.
 *
 *     Copyright (C) 2016 by ranger
 *     https://github.com/theranger/radagast
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ee.risk.radagast.processor.valence.wordlist;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordClassifier implements Classifier<Word, ValenceWordListResult> {
	private final Map<String, Integer> wordList = new HashMap<>();

	public WordClassifier(String wordListFile) throws IOException {
		String line;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(wordListFile))) {
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length < 2) continue;
				wordList.put(values[0], Integer.valueOf(values[1]));
			}
		}
	}

	public void classify(Word word, Result<Word, ValenceWordListResult> result) {
		ValenceWordListResult valenceWordListResult = (ValenceWordListResult) result;
		valenceWordListResult.setResult(wordList.getOrDefault(word.getValue(), 0));
	}
}
