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

import ee.risk.radagast.result.LexicalResult;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.radagast.tokenizer.Token;
import ee.risk.vabamorf.model.MorphInfo;
import ee.risk.vabamorf.model.Word;

import java.util.HashMap;
import java.util.List;

public class MorphologySentenceResult extends MorphologyResult<Sentence> {

	private HashMap<String, MorphInfo> wordMorphInfos = new HashMap<>();

	@Override
	public <S extends Token> void aggregate(Sentence sentence, S child, Result<S, MorphologyResult> result) {
		if (!(result instanceof MorphologyWordResult)) return;
		MorphInfo morphInfo = wordMorphInfos.get(child.getValue());

		// No morphology info found for this word. Do not aggregate further
		if (morphInfo == null) return;

		// If child result is a word result, fill child result object with data for model completeness.
		// Usually info should be aggregated from child, but this processor operates at sentence level.
		// Information must be re-populated back down to the child in this case.
		MorphologyWordResult wordResult = ((MorphologyWordResult) result);
		wordResult.setRoot(morphInfo.getRoot());
		wordResult.setType(LexicalResult.Type.parseFrom(morphInfo.getPos()));
		wordResult.setCount(1);

		// Is this lexical root already exists? If not, put in map, otherwise we need to merge info
		LexicalResult lexicalResult = wordResults.get(morphInfo.getRoot());
		if (lexicalResult == null) {
			wordResults.put(morphInfo.getRoot(), wordResult);
			return;
		}

		lexicalResult.mergeFrom(wordResult);
	}

	void addMorphologyWord(Word word) {
		List<MorphInfo> morphInfos = word.getMorphInfo();
		if (morphInfos == null || morphInfos.size() != 1) return;

		morphInfos.forEach(morphInfo -> {
			wordMorphInfos.put(word.getData().toLowerCase(), morphInfo);
		});
	}
}
