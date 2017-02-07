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

package ee.risk.radagast;

import ee.risk.radagast.dao.VocabularyDAO;

import java.io.IOException;

public class RaDB {

	public static void main(String args[]) throws IOException {
		if (args.length < 2) {
			printHelp();
			return;
		}

		if (args[0].equalsIgnoreCase("voc")) {
			VocabularyDAO vocabularyDAO = new VocabularyDAO("data/dictionary.db", "data/wordbook.db");
			System.out.println(vocabularyDAO.listAuthors());
			System.out.println(vocabularyDAO.listLemmas());
			return;
		}

		printHelp();
	}

	private static void printHelp() {
		System.out.println("Radagast DB query tool.\n\n" +
				"Usage: radb.java [database] [key]\n" +
				"	database - voc (vocabulary)\n" +
				"	key - map key value"
		);
	}
}
