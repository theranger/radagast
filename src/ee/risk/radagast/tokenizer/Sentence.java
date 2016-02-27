package ee.risk.radagast.tokenizer;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

/**
 * Created by ranger on 27.02.16.
 */
public class Sentence extends Token {

	public static final String separator = "[.!?]";

	public Sentence(String value) {
		super(value);

		String[] words = value.split(Word.separator);
		for(String word : words) {
			tokens.add(new Word(word));
		}
	}

	@Override
	public Result createResult(ResultFactory resultFactory) {
		return resultFactory.createSentenceResult(this);
	}
}
