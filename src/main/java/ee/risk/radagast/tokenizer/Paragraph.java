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

public class Paragraph extends Token<Paragraph, Sentence> {

	public static final String separator = "\\n";

	public Paragraph(String value) {
		super(value);

		String[] sentences = value.split(Sentence.separator);
		for (String sentence : sentences) {
			if (sentence.isEmpty()) continue;
			tokens.add(new Sentence(sentence));
		}
	}

	@Override
	public <R extends Result> Result<Paragraph, R> createResult(ResultFactory<R> resultFactory) {
		return resultFactory.createParagraphResult();
	}

	@Override
	public <R extends Result> Classifier<Paragraph, R> createClassifier(ClassifierFactory<R> classifierFactory) {
		return classifierFactory.createParagraphClassifier();
	}
}
