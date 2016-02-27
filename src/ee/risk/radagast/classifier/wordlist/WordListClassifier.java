package ee.risk.radagast.classifier.wordlist;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.tokenizer.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ranger on 27.02.16.
 */
public class WordListClassifier implements Classifier {

	private Map<String, Integer> wordList = new HashMap<>();
	private WordListResultFactory wordListResultFactory = new WordListResultFactory();

	public WordListClassifier(String wordListFile) throws IOException {
		String line = null;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(wordListFile))) {
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length < 2) continue;
				wordList.put(values[0], Integer.valueOf(values[1]));
			}
		}
	}

	@Override
	public void classify(Token token) {
		for(Token t : token.getTokens()) {
			classify(t);
			t.addResult(t.createResult(wordListResultFactory));
		}
	}
}
