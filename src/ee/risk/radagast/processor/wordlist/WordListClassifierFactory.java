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

package ee.risk.radagast.processor.wordlist;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.classifier.ClassifierFactory;
import ee.risk.radagast.classifier.GenericClassifier;
import ee.risk.radagast.tokenizer.Corpus;
import ee.risk.radagast.tokenizer.Paragraph;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.radagast.tokenizer.Word;

import java.io.IOException;

public class WordListClassifierFactory implements ClassifierFactory {

	Classifier<Word> wordClassifier;

	public WordListClassifierFactory(String wordFilePath) throws IOException {
		wordClassifier = new WordClassifier(wordFilePath);
	}

	@Override
	public Classifier<Word> createWordClassifier() {
		return wordClassifier;
	}

	@Override
	public Classifier<Sentence> createSentenceClassifier() {
		return new GenericClassifier<>();
	}

	@Override
	public Classifier<Paragraph> createParagraphClassifier() {
		return new GenericClassifier<>();
	}

	@Override
	public Classifier<Corpus> createCorpusClassifier() {
		return new GenericClassifier<>();
	}
}
