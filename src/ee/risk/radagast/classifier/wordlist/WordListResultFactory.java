package ee.risk.radagast.classifier.wordlist;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;
import ee.risk.radagast.tokenizer.Token;

/**
 * Created by ranger on 27.02.16.
 */
public class WordListResultFactory implements ResultFactory {

	@Override
	public Result.WordResult createWordResult(Token token) {
		return new WordListResult.WordResult(token);
	}

	@Override
	public Result.SentenceResult createSentenceResult(Token token) {
		return new WordListResult.SentenceResult(token);
	}

	@Override
	public Result.ParagraphResult createParagraphResult(Token token) {
		return new WordListResult.ParagraphResult(token);
	}

	@Override
	public Result.CorpusResult createCorpusResult(Token token) {
		return new WordListResult.CorpusResult(token);
	}
}
