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

package ee.risk.radagast.db;

import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StringListSerializer implements Serializer<List<String>>, Serializable {

	@Override
	public void serialize(@NotNull DataOutput2 out, @NotNull List<String> value) throws IOException {
		for (String v : value) out.writeUTF(v);
	}

	@Override
	public List<String> deserialize(@NotNull DataInput2 input, int available) throws IOException {
		List<String> ret = new ArrayList<>();

		while(input.getPos() < available) ret.add(input.readLine());

		return ret;
	}
}
