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

package ee.risk.radagast.processor.morphology;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;
import ee.risk.radagast.tokenizer.Corpus;
import ee.risk.radagast.tokenizer.Paragraph;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.radagast.tokenizer.Word;

public class MorphologyResultFactory implements ResultFactory<MorphologyResult> {
	@Override
	public Result<Word, MorphologyResult> createWordResult() {
		return new MorphologyResult<>();
	}

	@Override
	public Result<Sentence, MorphologyResult> createSentenceResult() {
		return new MorphologyResult<>();
	}

	@Override
	public Result<Paragraph, MorphologyResult> createParagraphResult() {
		return new MorphologyResult<>();
	}

	@Override
	public Result<Corpus, MorphologyResult> createCorpusResult() {
		return new MorphologyResult<>();
	}
}
