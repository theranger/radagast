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
import java.util.stream.Collectors;

public abstract class Token<T extends Token, S extends Token> {

	protected final ArrayList<S> tokens = new ArrayList<>();
	protected final ArrayList<Result<? extends Result>> results = new ArrayList<>();
	protected final String value;

	public Token(String value) {
		this.value = value;
	}

	public ArrayList<S> getTokens() {
		return tokens;
	}

	@SuppressWarnings("unchecked")
	public <R extends Result> ArrayList<R> getResults(R result) {
		ArrayList<R> ret = new ArrayList<>();
		Class resultClass = result.getClass();
		ret.addAll(results.stream().filter(r -> r.getClass().isAssignableFrom(resultClass)).map(r -> (R) r).collect(Collectors.toList()));
		return ret;
	}

	public <R extends Result> void addResult(Result<R> result) {
		results.add(result);
	}

	public String getValue() {
		return value;
	}

	public abstract <R extends Result<R>> R createResult(ResultFactory<R> resultFactory);

	public abstract <R extends Result> Classifier<T, R> createClassifier(ClassifierFactory<R> classifierFactory);

	@Override
	public String toString() {
		if(value != null) return value;
		return "<token>";
	}
}
