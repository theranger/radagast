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
import ee.risk.radagast.model.Entry;
import ee.risk.radagast.parser.ParserException;
import ee.risk.radagast.parser.TextFileParser;
import ee.risk.radagast.processor.morphology.MorphologyProcessor;
import ee.risk.radagast.processor.reductor.ReductionProcessor;
import ee.risk.radagast.processor.reductor.ReductionResult;
import ee.risk.radagast.processor.reputation.ReputationProcessor;
import ee.risk.radagast.processor.valence.bayes.ValenceBayesProcessor;
import ee.risk.radagast.processor.valence.wordlist.ValenceWordListProcessor;
import ee.risk.radagast.proto.Vocabulary;
import ee.risk.radagast.tokenizer.Corpus;

import java.io.IOException;

class Radagast {

	public static void main(String[] args) throws IOException, ParserException {
		if (args.length < 1) {
			printHelp();
			return;
		}

		TextFileParser textFileParser = new TextFileParser(args[0]);
		MorphologyProcessor morphologyProcessor = new MorphologyProcessor("lib/vabamorf/bin/et.dct", "lib/vabamorf/bin/et3.dct");
		ValenceWordListProcessor valenceWordListProcessor = new ValenceWordListProcessor("lib/valence/sqnad.csv");
		ValenceBayesProcessor valenceBayesProcessor = new ValenceBayesProcessor("lib/valence/korpus.csv");
		ReputationProcessor reputationProcessor = new ReputationProcessor("data/reputation.txt");
		ReductionProcessor reductionProcessor = new ReductionProcessor();

		// Databases
		VocabularyDAO vocabularyDAO = new VocabularyDAO("data/dictionary.db", "data/wordbook.db");

		Entry entry;
		while ((entry = textFileParser.parse()) != null) {
			reputationProcessor.process(entry);

			Corpus corpus = entry.getContent();
			morphologyProcessor.process(corpus);
			valenceWordListProcessor.process(corpus);
			valenceBayesProcessor.process(corpus);
			ReductionResult results = reductionProcessor.reduce(entry);

			System.out.println(entry);
			System.out.println(results);
			System.out.println();
			System.out.println();

			// Update databases
			String name = entry.getName();
			results.getLexicalEntries().forEach((key, value) -> {
				Vocabulary.Lemma lemma = vocabularyDAO.getLemma(key);
				Vocabulary.Author author = vocabularyDAO.getAuthor(name);
				vocabularyDAO.addAuthor(lemma, author);
			});
		}

		vocabularyDAO.save();
	}

	private static void printHelp() {
		System.out.println("Radagast is a classification framework for user submitted content.\n\n" +
				"Usage: radagast.java [entries_file]\n" +
				"	entries_file - file name where analyzable content is located.\n"
		);
	}
}
