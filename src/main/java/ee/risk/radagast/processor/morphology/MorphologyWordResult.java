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

import ee.risk.radagast.tokenizer.Word;
import org.jetbrains.annotations.Nullable;

public class MorphologyWordResult extends MorphologyResult<Word> {

	public enum Type {
		ADJECTIVE("A"), VERB("V"), NOUN("N"), UNKNOWN("Z");

		private final String value;

		Type(final String value) {
			this.value = value;
		}

		static Type parseFrom(String name) {
			if (name.equalsIgnoreCase("A")) return ADJECTIVE;
			if (name.equalsIgnoreCase("V")) return VERB;
			return UNKNOWN;
		}
	}

	private String value;
	private String root;
	private Type type = Type.UNKNOWN;
	private int count = 0;

	public MorphologyWordResult copyFrom(@Nullable MorphologyWordResult result) {
		if (result == null) return this;
		if (root != null && root.equalsIgnoreCase(result.root)) return this;
		if (type != Type.UNKNOWN && type != result.type) return this;

		root = result.root;
		type = result.type;
		count += result.count;
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

	public void setCount(int count) {
		this.count = count;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
