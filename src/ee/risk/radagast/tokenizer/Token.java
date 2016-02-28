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

package ee.risk.radagast.tokenizer;

import ee.risk.radagast.classifier.Classifier;
import ee.risk.radagast.classifier.ClassifierFactory;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

import java.util.ArrayList;

public abstract class Token<T extends Token, S extends Token> {

	protected final ArrayList<S> tokens = new ArrayList<>();
	protected final ArrayList<Result> results = new ArrayList<>();
	protected final String value;

	public Token(String value) {
		this.value = value;
	}

	public ArrayList<S> getTokens() {
		return tokens;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void addResult(Result result) {
		results.add(result);
	}

	public String getValue() {
		return value;
	}

	public abstract Result<T> createResult(ResultFactory resultFactory);

	public abstract Classifier<T> createClassifier(ClassifierFactory classifierFactory);

	@Override
	public String toString() {
		if(value != null) return value;
		return "<token>";
	}
}
