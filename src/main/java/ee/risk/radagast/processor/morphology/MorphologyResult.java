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

import ee.risk.radagast.processor.reductor.ReductionResult;
import ee.risk.radagast.result.LexicalResult;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Token;

import java.util.HashMap;

public class MorphologyResult<T extends Token> implements Result<T, MorphologyResult> {

	HashMap<String, LexicalResult> wordResults = new HashMap<>();

	@Override
	public <S extends Token> void aggregate(T token, S child, Result<S, MorphologyResult> result) {
		HashMap<String, LexicalResult> childResults = ((MorphologyResult) result).wordResults;

		childResults.forEach((key, value) -> {
			wordResults.put(value.getRoot(), wordResults.getOrDefault(value.getRoot(), new MorphologyWordResult()).mergeFrom(value));
		});
	}

	@Override
	public void onPostAggregate() {

	}

	@Override
	public void reduce(ReductionResult result) {
		result.setLexicalEntries(wordResults);
	}

}
