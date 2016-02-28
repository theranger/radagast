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

public abstract class Processor {

	private final ResultFactory resultFactory;
	private final ClassifierFactory classifierFactory;

	protected Processor(ClassifierFactory classifierFactory, ResultFactory resultFactory) {
		this.classifierFactory = classifierFactory;
		this.resultFactory = resultFactory;
	}

	public void process(Corpus corpus) {
		corpus.getTokens().forEach(this::process);
		Classifier<Corpus> classifier = corpus.createClassifier(classifierFactory);
		Result<Corpus> result = corpus.createResult(resultFactory);
		classifier.classify(corpus, result);
		corpus.getTokens().forEach(paragraph -> result.aggregate(paragraph.getResults()));
		corpus.addResult(result);
	}

	private void process(Paragraph paragraph) {
		paragraph.getTokens().forEach(this::process);
		Classifier<Paragraph> classifier = paragraph.createClassifier(classifierFactory);
		Result<Paragraph> result = paragraph.createResult(resultFactory);
		classifier.classify(paragraph, result);
		paragraph.getTokens().forEach(sentence -> result.aggregate(sentence.getResults()));
		paragraph.addResult(result);
	}

	private void process(Sentence sentence) {
		sentence.getTokens().forEach(this::process);
		Classifier<Sentence> classifier = sentence.createClassifier(classifierFactory);
		Result<Sentence> result = sentence.createResult(resultFactory);
		classifier.classify(sentence, result);
		sentence.getTokens().forEach(word -> result.aggregate(word.getResults()));
		sentence.addResult(result);
	}

	private void process(Word word) {
		Classifier<Word> classifier = word.createClassifier(classifierFactory);
		Result<Word> result = word.createResult(resultFactory);
		classifier.classify(word, result);
		word.addResult(result);
	}
}
