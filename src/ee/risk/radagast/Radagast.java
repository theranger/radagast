package ee.risk.radagast;

import ee.risk.radagast.classifier.wordlist.WordListClassifier;
import ee.risk.radagast.log.Log;
import ee.risk.radagast.tokenizer.Corpus;

import java.io.IOException;

/**
 * Created by ranger on 27.02.16.
 */
public class Radagast {

	public static void main(String[] args) throws IOException {
		Corpus corpus = new Corpus("Täna oli väga ilus päev!");
		WordListClassifier wordListClassifier = new WordListClassifier("data/sqnad.csv");
		wordListClassifier.classify(corpus);
	}
}
