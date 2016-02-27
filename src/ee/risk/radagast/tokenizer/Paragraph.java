package ee.risk.radagast.tokenizer;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

/**
 * Created by ranger on 27.02.16.
 */
public class Paragraph extends Token {

	public static final String separator = "\\n";

	public Paragraph(String value) {
		super(value);

		String[] sentences = value.split(Sentence.separator);
		for (String sentence : sentences) {
			tokens.add(new Sentence(sentence));
		}
	}

	@Override
	public Result createResult(ResultFactory resultFactory) {
		return resultFactory.createParagraphResult(this);
	}
}
