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

package ee.risk.radagast;

import ee.risk.radagast.processor.wordlist.WordListProcessor;
import ee.risk.radagast.tokenizer.Corpus;

import java.io.IOException;

class Radagast {

	public static void main(String[] args) throws IOException {
		Corpus corpus = new Corpus("Täna oli väga ilus päev!");

		WordListProcessor wordListProcessor = new WordListProcessor("../sqnad.csv");
		wordListProcessor.process(corpus);
	}
}
