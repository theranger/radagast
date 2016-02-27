package ee.risk.radagast.tokenizer;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

/**
 * Created by ranger on 27.02.16.
 */
public class Corpus extends Token {

	public Corpus(String value) {
		super(value);

		String [] paragraphs = value.split(Paragraph.separator);
		for(String paragraph : paragraphs) {
			tokens.add(new Paragraph(paragraph));
		}
	}

	@Override
	public Result createResult(ResultFactory resultFactory) {
		return resultFactory.createCorpusResult(this);
	}
}
