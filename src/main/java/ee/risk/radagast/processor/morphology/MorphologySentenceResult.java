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

import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Sentence;
import ee.risk.radagast.tokenizer.Token;
import ee.risk.vabamorf.model.MorphInfo;
import ee.risk.vabamorf.model.Word;

import java.util.List;

public class MorphologySentenceResult extends MorphologyResult<Sentence> {

	@Override
	public <S extends Token> void aggregate(Sentence sentence, S child, Result<S, MorphologyResult> result) {
		if (!(child instanceof ee.risk.radagast.tokenizer.Word)) return;

		((MorphologyWordResult) result).mergeFrom(wordResults.get(child.getValue()));
	}

	void addMorphologyWord(Word word) {
		List<MorphInfo> morphInfos = word.getMorphInfo();
		if (morphInfos == null || morphInfos.size() != 1) return;

		morphInfos.forEach(morphInfo -> {
			MorphologyWordResult wordResult = new MorphologyWordResult();
			wordResult.setRoot(morphInfo.getRoot());
			wordResult.setType(MorphologyWordResult.Type.parseFrom(morphInfo.getPos()));
			wordResults.put(word.getData().toLowerCase().toLowerCase(), wordResult);
		});
	}
}
