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

package ee.risk.radagast.processor;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.classifier.ClassifierFactory;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;
import ee.risk.radagast.tokenizer.Corpus;
import ee.risk.radagast.tokenizer.Paragraph;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.radagast.tokenizer.Word;

@SuppressWarnings("unchecked")
public abstract class TokenProcessor<R extends Result> {

	private final ResultFactory<R> resultFactory;
	private final ClassifierFactory<R> classifierFactory;

	public TokenProcessor(ClassifierFactory<R> classifierFactory, ResultFactory<R> resultFactory) {
		this.classifierFactory = classifierFactory;
		this.resultFactory = resultFactory;
	}

	public void process(Corpus corpus) {
		corpus.getTokens().forEach(this::process);
		Classifier<Corpus, R> classifier = corpus.createClassifier(classifierFactory);
		Result<Corpus, R> result = corpus.createResult(resultFactory);
		classifier.classify(corpus, result);

		for(Paragraph p : corpus.getTokens()) {
			p.getResults().forEach(r -> {
				try {
					result.aggregate(corpus, p, (Result<Paragraph, R>) r);
				}
				catch (ClassCastException ignored) {}
			});
		}

		result.onPostAggregate();
		corpus.addResult(result);
	}

	private void process(Paragraph paragraph) {
		paragraph.getTokens().forEach(this::process);
		Classifier<Paragraph, R> classifier = paragraph.createClassifier(classifierFactory);
		Result<Paragraph, R> result = paragraph.createResult(resultFactory);
		classifier.classify(paragraph, result);

		for(Sentence s : paragraph.getTokens()) {
			s.getResults().forEach(r -> {
				try {
					result.aggregate(paragraph, s, (Result<Sentence, R>) r);
				}
				catch (ClassCastException ignored) {}
			});
		}

		result.onPostAggregate();
		paragraph.addResult(result);
	}

	private void process(Sentence sentence) {
		sentence.getTokens().forEach(this::process);
		Classifier<Sentence, R> classifier = sentence.createClassifier(classifierFactory);
		Result<Sentence, R> result = sentence.createResult(resultFactory);
		classifier.classify(sentence, result);

		for(Word w : sentence.getTokens()) {
			w.getResults().forEach(r -> {
				try {
					result.aggregate(sentence, w, (Result<Word, R>) r);
				}
				catch (ClassCastException ignored) {}
			});
		}

		result.onPostAggregate();
		sentence.addResult(result);
	}

	private void process(Word word) {
		Classifier<Word, R> classifier = word.createClassifier(classifierFactory);
		Result<Word, R> result = word.createResult(resultFactory);
		classifier.classify(word, result);

		result.onPostAggregate();
		word.addResult(result);
	}
}
