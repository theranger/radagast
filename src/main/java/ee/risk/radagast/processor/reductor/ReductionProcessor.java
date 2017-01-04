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

package ee.risk.radagast.processor.reductor;

import ee.risk.radagast.model.Entry;

public class ReductionProcessor {
	public ReductionResult reduce(Entry entry) {
		ReductionResult reductionResult = new ReductionResult();

		getContentResult(entry, reductionResult);
		getTitleResult(entry, reductionResult);
		getEntryResult(entry, reductionResult);
		return reductionResult;
	}

	public ReductionResult getEntryResult(Entry entry) {
		return getEntryResult(entry, new ReductionResult());
	}

	public ReductionResult getTitleResult(Entry entry) {
		return getTitleResult(entry, new ReductionResult());
	}

	public ReductionResult getContentResult(Entry entry) {
		return getContentResult(entry, new ReductionResult());
	}

	private ReductionResult getEntryResult(Entry entry, ReductionResult reductionResult) {
		try {
			entry.getResults().forEach(entryResult -> entryResult.reduce(reductionResult));
		}
		catch (NullPointerException ignored) {}
		return reductionResult;
	}

	private ReductionResult getTitleResult(Entry entry, ReductionResult reductionResult) {
		try {
			entry.getTitle().getResults().forEach(corpusResult -> corpusResult.reduce(reductionResult));
		}
		catch (NullPointerException ignored) {}
		return reductionResult;
	}

	private ReductionResult getContentResult(Entry entry, ReductionResult reductionResult) {
		try {
			entry.getContent().getResults().forEach(corpusResult -> corpusResult.reduce(reductionResult));
		}
		catch (NullPointerException ignored) {}
		return reductionResult;
	}
}
