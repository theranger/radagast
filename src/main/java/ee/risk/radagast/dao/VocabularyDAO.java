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

package ee.risk.radagast.dao;

import ee.risk.radagast.db.StringListSerializer;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class VocabularyDAO {
	private static final String DB_FILE = "vocabulary.db";
	private static final String DB_ROOT = "map";

	ConcurrentMap<String, List<String>> map;

	public VocabularyDAO() {
		this(DB_FILE);
	}

	public VocabularyDAO(String dbPath) {
		DB db = DBMaker.fileDB(dbPath).closeOnJvmShutdown().make();
		map = db.hashMap(DB_ROOT, Serializer.STRING, new StringListSerializer()).createOrOpen();
	}

	public List<String> getUsers(String word) {
		return map.getOrDefault(word, new ArrayList<>());
	}

	public void addUser(String word, String user) {
		List<String> users = getUsers(word);
		users.add(user);
		map.put(word, users);
	}

	public int getSize() {
		return map.size();
	}

}
