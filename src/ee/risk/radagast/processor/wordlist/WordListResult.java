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

import ee.risk.radagast.log.Log;
import ee.risk.radagast.result.Result;

public class WordListResult implements Result<WordListResult> {
	protected Log log = Log.getLogger(Log.Level.DEBUG);
	public int value = 0;

	@Override
	public void aggregate(WordListResult result) {
		log.debug("Result: %d, %d", value, result.value);
		value += result.value;
	}

	@Override
	public void reduce(Result result) {

	}

	static class WordListCorpusResult extends WordListResult {

		@Override
		public void aggregate(WordListResult result) {
			value += result.value;
			log.debug("Corpus result: %.2f", (double)value / 100);
		}
	}
}
