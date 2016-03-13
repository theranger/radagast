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

package ee.risk.radagast.parser;

import ee.risk.radagast.io.GenericFileParser;
import ee.risk.radagast.model.Entry;
import ee.risk.radagast.tokenizer.Corpus;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFileParser extends GenericFileParser<Entry> {

	private enum State { WAIT_HEADER, HEADER, BODY, SEPARATOR, ENTRY }
	private Entry entry;
	private StringBuffer sb;

	public TextFileParser(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	@Override
	public Entry parse() throws IOException {
		State state = State.WAIT_HEADER;
		sb = new StringBuffer();
		entry = new Entry();
		String line;

		while ((line = reader.readLine()) != null) {
			switch (state) {
				case WAIT_HEADER:
					if (line.isEmpty()) break;

				case HEADER:
					state = parseHeader(line);
					break;

				case BODY:
					state = parseBody(line);
					break;

				case SEPARATOR:
					state = parseSeparator(line);
					break;
			}

			if (state == State.ENTRY) return entry;
		}

		if (state == State.WAIT_HEADER) return null;
		entry.setContent(new Corpus(sb.toString()));
		return entry;
	}

	private State parseHeader(String line) {
		if (line.isEmpty()) return State.BODY;

		String[] tokens = line.split(":");
		if (tokens.length < 2) return State.HEADER;

		if (tokens[0].equalsIgnoreCase("subject")) {
			entry.setTitle(new Corpus(tokens[1]));
			return State.HEADER;
		}

		if (tokens[0].equalsIgnoreCase("from")) {
			entry.setName(tokens[1]);
			return State.HEADER;
		}

		return State.HEADER;
	}

	private State parseBody(String line) {
		if (line.isEmpty()) return State.SEPARATOR;

		sb.append(line);
		sb.append("\n");
		return State.BODY;
	}

	private State parseSeparator(String line) {
		if (line.isEmpty()) {
			entry.setContent(new Corpus(sb.toString()));
			return State.ENTRY;
		}

		sb.append(line);
		return State.BODY;
	}
}
