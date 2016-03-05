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

package ee.risk.radagast.lib;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountMap<K extends Enum<K>> extends EnumMap<K, Integer> {

	private final Class<K> keyType;

	public CountMap(Class<K> keyType) {
		super(keyType);
		this.keyType = keyType;
	}

	public void add(K key) {
		Integer count = getOrDefault(key, 0);
		put(key, count + 1);
	}

	public Map<K, Integer> getMax() {
		if (isEmpty()) return new CountMap<>(keyType);

		final Entry<K, Integer> max = entrySet()
				.stream()
				.max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
				.get();

		return entrySet()
				.stream()
				.filter(e -> e.getValue().equals(max.getValue()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[");
		entrySet().forEach(e -> sb.append(" ").append(e.getKey()).append(":").append(e.getValue()));
		return sb.append(" ]").toString();
	}
}
