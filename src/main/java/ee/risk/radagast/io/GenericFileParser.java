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

package ee.risk.radagast.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class GenericFileParser<T> implements FileParser<T> {

	protected BufferedReader reader;

	public GenericFileParser(String fileName) throws FileNotFoundException {
		open(fileName);
	}

	@Override
	public void open(String fileName) throws FileNotFoundException {
		close();
		reader = new BufferedReader(new FileReader(fileName));
	}

	@Override
	public void close() {
		try {
			if (reader == null) return;
			reader.close();
		}
		catch (IOException ignored) { }
	}
}
