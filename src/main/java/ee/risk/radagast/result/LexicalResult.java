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

package ee.risk.radagast.result;

import com.sun.istack.internal.Nullable;

public interface LexicalResult {

	enum Type {
		ADJECTIVE("A"), VERB("V"), NOUN("N"), PRONOUN("P"), CONJUNCTION("J"), UNKNOWN("Z");

		private final String value;

		Type(final String value) {
			this.value = value;
		}

		public static Type parseFrom(String name) {
			if (name.equalsIgnoreCase("A")) return ADJECTIVE;
			if (name.equalsIgnoreCase("V")) return VERB;
			if (name.equalsIgnoreCase("J")) return CONJUNCTION;
			if (name.equalsIgnoreCase("P")) return PRONOUN;
			return UNKNOWN;
		}
	}

	String getRoot();
	Type getType();
	int getCount();
	LexicalResult mergeFrom(@Nullable LexicalResult result);
}
