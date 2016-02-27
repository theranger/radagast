package ee.risk.radagast.tokenizer;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

/**
 * Created by ranger on 27.02.16.
 */
public class Word extends Token {

	public static final String separator = "[,\\s]";

	public Word(String value) {
		super(value);
	}

	@Override
	public Result createResult(ResultFactory resultFactory) {
		return resultFactory.createWordResult(this);
	}
}
