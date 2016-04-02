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

package ee.risk.radagast.model;

import ee.risk.radagast.result.EntryResult;
import ee.risk.radagast.tokenizer.Corpus;

import java.util.ArrayList;

public class Entry {
	private Corpus content;
	private Corpus title;
	private String name;

	private ArrayList<EntryResult> results = new ArrayList<>();

	public ArrayList<EntryResult> getResults() {
		return results;
	}

	public void addResult(EntryResult result) {
		results.add(result);
	}

	public Corpus getContent() {
		return content;
	}

	public void setContent(Corpus content) {
		this.content = content;
	}

	public Corpus getTitle() {
		return title;
	}

	public void setTitle(Corpus title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReducedResult getResult() {
		return getResult(new ReducedResult());
	}

	public ReducedResult getResult(ReducedResult reducedResult) {
		results.forEach(entryResult -> entryResult.reduce(reducedResult));
		return reducedResult;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!name.isEmpty()) {
			sb.append("From: ");
			sb.append(name);
			sb.append("\n");
		}

		sb.append(content);

		return sb.toString();
	}
}
