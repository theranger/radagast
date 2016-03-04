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

public abstract class Processor<R extends Result<R>> {

	private final ResultFactory<R> resultFactory;
	private final ClassifierFactory<R> classifierFactory;

	protected Processor(ClassifierFactory<R> classifierFactory, ResultFactory<R> resultFactory) {
		this.classifierFactory = classifierFactory;
		this.resultFactory = resultFactory;
	}

	public void process(Corpus corpus) {
		corpus.getTokens().forEach(this::process);
		Classifier<Corpus, R> classifier = corpus.createClassifier(classifierFactory);
		R result = corpus.createResult(resultFactory);
		classifier.classify(corpus, result);
		corpus.getTokens().forEach(paragraph -> paragraph.getResults(result).forEach(result::aggregate));
		corpus.addResult(result);
	}

	private void process(Paragraph paragraph) {
		paragraph.getTokens().forEach(this::process);
		Classifier<Paragraph, R> classifier = paragraph.createClassifier(classifierFactory);
		R result = paragraph.createResult(resultFactory);
		classifier.classify(paragraph, result);
		paragraph.getTokens().forEach(sentence -> sentence.getResults(result).forEach(result::aggregate));
		paragraph.addResult(result);
	}

	private void process(Sentence sentence) {
		sentence.getTokens().forEach(this::process);
		Classifier<Sentence, R> classifier = sentence.createClassifier(classifierFactory);
		R result = sentence.createResult(resultFactory);
		classifier.classify(sentence, result);
		sentence.getTokens().forEach(word -> word.getResults(result).forEach(result::aggregate));
		sentence.addResult(result);
	}

	private void process(Word word) {
		Classifier<Word, R> classifier = word.createClassifier(classifierFactory);
		R result = word.createResult(resultFactory);
		classifier.classify(word, result);
		word.addResult(result);
	}
}
