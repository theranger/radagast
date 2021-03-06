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

package ee.risk.radagast.processor.reductor;

import ee.risk.radagast.result.LexicalResult;

import java.util.HashMap;

public class ReductionResult {
	private double value = 0;
	private HashMap<String, LexicalResult> lexicalEntries;

	public HashMap<String, LexicalResult> getLexicalEntries() {
		return lexicalEntries;
	}

	public void setLexicalEntries(HashMap<String, LexicalResult> lexicalEntries) {
		this.lexicalEntries = lexicalEntries;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuffer lexicon = new StringBuffer();

		lexicalEntries.forEach((key, value) -> {
			lexicon.append("\t" + value.getRoot() + " (" + value.getCount() + ")\n");
		});


		return String.valueOf(value) + "\n" + lexicon;
	}
}
