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
	protected final ArrayList<Result<T, ? extends Result>> results = new ArrayList<>();
	protected final String value;

	public Token(String value) {
		this.value = value;
	}

	public ArrayList<S> getTokens() {
		return tokens;
	}

	@SuppressWarnings("unchecked")
	public <R extends Result> ArrayList<Result<T, ?>> getResults() {
		return results;
	}

	public <R extends Result> void addResult(Result<T, R> result) {
		results.add(result);
	}

	public String getValue() {
		return value;
	}

	public abstract <R extends Result> Result<T, R> createResult(ResultFactory<R> resultFactory);

	public abstract <R extends Result> Classifier<T, R> createClassifier(ClassifierFactory<R> classifierFactory);

	@Override
	public String toString() {
		if(value != null) return value;
		return "<token>";
	}
}
