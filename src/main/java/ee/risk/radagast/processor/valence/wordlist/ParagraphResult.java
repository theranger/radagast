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

import ee.risk.radagast.tokenizer.Paragraph;

public class ParagraphResult extends ValenceWordListResult<Paragraph> {
	@Override
	public void onPostAggregate() {
		if (values.containsKey(Valence.EXTREME)) {
			values.clear();
			values.set(Valence.NEGATIVE, wordCount);
			return;
		}

		int positive = values.getOrDefault(Valence.POSITIVE, 0);
		int negative = values.getOrDefault(Valence.NEGATIVE, 0);
		values.clear();

		if (positive > negative) {
			values.set(Valence.POSITIVE, wordCount);
			return;
		}

		if (negative > positive) {
			values.set(Valence.NEGATIVE, wordCount);
			return;
		}

		if (positive == negative) {
			values.set(Valence.MIXED, wordCount);
			return;
		}

		values.set(Valence.NEUTRAL, wordCount);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + values.toString();
	}
}
