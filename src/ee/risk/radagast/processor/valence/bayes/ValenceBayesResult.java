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

package ee.risk.radagast.processor.valence.bayes;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Token;

public class ValenceBayesResult<T extends Token> implements Result<T, ValenceBayesResult> {
	public float value = 0f;
	public String category;

	@Override
	public <S extends Token> void aggregate(T token, S child, Result<S, ValenceBayesResult> result) {
		ValenceBayesResult<S> valenceBayesResult = (ValenceBayesResult<S>) result;

		value += valenceBayesResult.value;
		category = valenceBayesResult.category == null ? category : valenceBayesResult.category;
	}

	@Override
	public void reduce(Result result) {

	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + value + ": " + category;
	}
}
