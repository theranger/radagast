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

import ee.risk.radagast.tokenizer.Corpus;

import java.util.Map;

public class CorpusResult extends ValenceWordListResult<Corpus> {

	private enum Quantifier { ONLY, MOSTLY }

	private Quantifier quantifier = Quantifier.MOSTLY;
	private Valence result = Valence.MIXED;

	@Override
	public void onPostAggregate() {
		if(values.size() == 1) {
			// One emotion which is also a maximum

			quantifier = Quantifier.ONLY;
			values.forEach((valence, integer) -> result = valence);
			return;
		}

		float total = values.getTotal();
		Map<Valence, Integer> maximums = values.getMax();

		// Multiple maximums or too many emotions, return "mostly mixed"
		if (maximums.size() > 1 || values.size() > 2) return;

		// Two emotions. One should have more than twice of the words to be considered reliable
		// Use forEach lambda expression since maximums now have only one value
		maximums.forEach((valence, value) -> result = total / value < 1.5 ? valence : Valence.MIXED);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + quantifier + " " + result;
	}
}
