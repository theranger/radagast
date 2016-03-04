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

package ee.risk.radagast.tokenizer;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.classifier.ClassifierFactory;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

public class Sentence extends Token<Sentence, Word> {

	public static final String separator = "[.!?]";

	public Sentence(String value) {
		super(value);

		String[] words = value.split(Word.separator);
		for(String word : words) {
			tokens.add(new Word(word));
		}
	}

	@Override
	public <R extends Result<R>> R createResult(ResultFactory<R> resultFactory) {
		return resultFactory.createSentenceResult();
	}

	@Override
	public <R extends Result> Classifier<Sentence, R> createClassifier(ClassifierFactory<R> classifierFactory) {
		return classifierFactory.createSentenceClassifier();
	}
}
