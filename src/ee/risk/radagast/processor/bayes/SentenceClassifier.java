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

package ee.risk.radagast.processor.bayes;

import de.daslaboratorium.machinelearning.classifier.BayesClassifier;
import de.daslaboratorium.machinelearning.classifier.Classification;
import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.log.Log;
import ee.risk.radagast.tokenizer.Sentence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SentenceClassifier implements Classifier<Sentence, BayesResult> {
	private Log log = Log.getLogger(Log.Level.DEBUG);
	private BayesClassifier<String, String> bayesClassifier = new BayesClassifier<>();

	public SentenceClassifier(String trainingCorpusFile) throws IOException {
		String line;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(trainingCorpusFile))) {
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length < 2) continue;

				bayesClassifier.learn(values[0], Arrays.asList(values[1].split("\\s")));
			}
		}
	}

	@Override
	public void classify(Sentence token, BayesResult result) {
		ArrayList <String> words = new ArrayList<>();
		token.getTokens().forEach(word -> words.add(word.getValue()));

		Classification ret = bayesClassifier.classify(words);
		result.value = ret.getProbability();
		result.category = ret.getCategory().toString();
	}
}
