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

package ee.risk.radagast.processor;

import ee.risk.radagast.classifier.EntryClassifier;
import ee.risk.radagast.model.Entry;
import ee.risk.radagast.result.EntryResult;
import ee.risk.radagast.result.EntryResultFactory;

public abstract class EntryProcessor<R extends EntryResult> {

	private final EntryResultFactory<R> entryResultFactory;
	private final EntryClassifier<R> entryClassifier;

	public EntryProcessor(EntryClassifier<R> entryClassifier, EntryResultFactory<R> entryResultFactory) {
		this.entryClassifier = entryClassifier;
		this.entryResultFactory = entryResultFactory;
	}

	public void process(Entry entry) {
		EntryResult<R> result = entryResultFactory.createEntryResult();

		entryClassifier.classify(entry, result);
		entry.addResult(result);
	}
}
