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

import ee.risk.radagast.proto.Vocabulary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class VocabularyDAO {

	private Vocabulary.Dictionary dictionary;
	private Vocabulary.Wordbook wordbook;

	private String dictionaryPath;
	private String wordbookPath;

	public VocabularyDAO(String dictionaryPath, String wordbookPath) throws IOException {
		this.dictionaryPath = dictionaryPath;
		this.wordbookPath = wordbookPath;

		try {
			dictionary = Vocabulary.Dictionary.parseFrom(new FileInputStream(dictionaryPath));
			wordbook = Vocabulary.Wordbook.parseFrom(new FileInputStream(wordbookPath));
		}
		catch (FileNotFoundException ex) {
			dictionary = Vocabulary.Dictionary.getDefaultInstance();
			wordbook = Vocabulary.Wordbook.getDefaultInstance();
		}
	}

	public Collection<Vocabulary.Author> listAuthors() {
		return wordbook.getAuthorsMap().values();
	}

	public Collection<Vocabulary.Lemma> listLemmas() {
		return dictionary.getLemmasMap().values();
	}

	public Vocabulary.Lemma getLemma(String name) {
		return dictionary.getLemmasOrDefault(name, Vocabulary.Lemma.newBuilder().setName(name).build());
	}

	public Vocabulary.Author getAuthor(String name) {
		return wordbook.getAuthorsOrDefault(name, Vocabulary.Author.newBuilder().setName(name).build());
	}

	public void addAuthor(Vocabulary.Lemma lemma, Vocabulary.Author author) {

		wordbook = wordbook.toBuilder().putAuthors(
				author.getName(),
				author.toBuilder()
						.putLemmas(lemma.getName(), lemma)
						.build()
		).build();

		dictionary = dictionary.toBuilder().putLemmas(
				lemma.getName(),
				lemma.toBuilder()
						.setCount(lemma.getCount() + 1)
						.addAuthors(author)
						.build()
		).build();
	}

	public void save() throws IOException {
		dictionary.writeTo(new FileOutputStream(dictionaryPath));
		wordbook.writeTo(new FileOutputStream(wordbookPath));
	}
}
