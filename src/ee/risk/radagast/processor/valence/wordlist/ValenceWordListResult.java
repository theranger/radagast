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

package ee.risk.radagast.processor.valence.wordlist;

import ee.risk.radagast.lib.CountMap;
import ee.risk.radagast.log.Log;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Paragraph;
import ee.risk.radagast.tokenizer.Token;

public class ValenceWordListResult<T extends Token> implements Result<T, ValenceWordListResult> {
	public enum Valence { POSITIVE, NEGATIVE, EXTREME, MIXED }

	protected int wordCount;
	protected CountMap<Valence> values = new CountMap<>(Valence.class);
	protected Log log = Log.getLogger(Log.Level.DEBUG);

	@Override
	public <S extends Token> void aggregate(T token, S child, Result<S, ValenceWordListResult> result) {
		ValenceWordListResult<S> valenceWordListResult = (ValenceWordListResult<S>) result;
		values.merge(valenceWordListResult.values);
		wordCount += valenceWordListResult.wordCount;
	}

	@Override
	public void reduce(Result result) {

	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public void setResult(int valence) {
		if (valence > 0)
			values.add(Valence.POSITIVE);
		else if (valence == -8)
			values.add(Valence.EXTREME);
		else if (valence < 0)
			values.add(Valence.NEGATIVE);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + values.toString();
	}


	public static class ParagraphResult extends ValenceWordListResult<Paragraph> {
		@Override
		public <S extends Token> void aggregate(Paragraph token, S child, Result<S, ValenceWordListResult> result) {
			ValenceWordListResult<S> r = (ValenceWordListResult<S>) result;
			wordCount += r.wordCount;

			if (r.values.containsKey(Valence.EXTREME)) {
				values.set(Valence.NEGATIVE, r.wordCount);
				return;
			}

			int positive = r.values.getOrDefault(Valence.POSITIVE, 0);
			int negative = r.values.getOrDefault(Valence.NEGATIVE, 0);

			if (positive > negative) {
				values.set(Valence.POSITIVE, r.wordCount);
				return;
			}

			if (negative > positive) {
				values.set(Valence.NEGATIVE, r.wordCount);
				return;
			}

			if (positive == negative) values.set(Valence.MIXED, r.wordCount);
		}
	}
}
