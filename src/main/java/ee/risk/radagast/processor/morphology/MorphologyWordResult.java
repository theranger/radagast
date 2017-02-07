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

package ee.risk.radagast.processor.morphology;

import com.sun.istack.internal.Nullable;
import ee.risk.radagast.result.LexicalResult;
import ee.risk.radagast.tokenizer.Word;

public class MorphologyWordResult extends MorphologyResult<Word> implements LexicalResult {

	private String root;
	private Type type = Type.UNKNOWN;
	private int count = 0;

	public LexicalResult mergeFrom(@Nullable LexicalResult result) {
		if (result == null) return this;
		if (root != null && !root.equalsIgnoreCase(result.getRoot())) return this;
		if (type != Type.UNKNOWN && type != result.getType()) return this;

		root = result.getRoot();
		type = result.getType();
		count += result.getCount();
		return this;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	void setCount(int count) {
		this.count = count;
	}
}
