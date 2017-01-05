/*
 *     Radagast is a classification framework for user submitted textual content.
 *
 *     Copyright (C) 2017 by ranger
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

package ee.risk.radagast.processor.morphology;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.log.Log;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.vabamorf.DisambiguatorException;
import ee.risk.vabamorf.JVabamorf;
import ee.risk.vabamorf.LinguisticException;


public class SentenceClassifier implements Classifier<Sentence, MorphologyResult> {

	private JVabamorf jVabamorf;
	private Log log = Log.getLogger(Log.Level.DEBUG);

	public SentenceClassifier(String linguisticsFilePath, String disambiguatorFilePath) {
		jVabamorf = new JVabamorf(linguisticsFilePath, disambiguatorFilePath);
	}

	@Override
	public void classify(Sentence token, Result<Sentence, MorphologyResult> result) {
		try {
			ee.risk.vabamorf.model.Sentence vabamorfSentence = jVabamorf.parseSentence(token.getValue());
			if (vabamorfSentence.getWords() == null) return;

			MorphologySentenceResult sentenceResult = (MorphologySentenceResult) result;
			vabamorfSentence.getWords().forEach(sentenceResult::addMorphologyWord);
		}
		catch (LinguisticException ex) {
			log.debug("Linguistics error: %s", ex.getMessage());
		}
		catch (DisambiguatorException ex) {
			log.debug("Disambiguator error: %s", ex.getMessage());
		}
	}
}
