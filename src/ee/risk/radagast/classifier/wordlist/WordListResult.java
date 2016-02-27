package ee.risk.radagast.classifier.wordlist;

import ee.risk.radagast.log.Log;
import ee.risk.radagast.result.Result;
import ee.risk.radagast.tokenizer.Token;

/**
 * Created by ranger on 27.02.16.
 */
public class WordListResult implements Result {
	private static final Log log = Log.getLogger(Log.Level.DEBUG);
	protected int value = 0;

	public WordListResult(Token token) {
		for (Token sub : token.getTokens()) {
			sub.getResults().forEach(this::aggregate);
		}
	}

	@Override
	public void aggregate(Result result) {
		if (result instanceof WordListResult) {
			this.value += ((WordListResult) result).value;
		}
	}

	@Override
	public void reduce(Result result) {

	}

	public static class WordResult extends WordListResult implements Result.WordResult {

		public WordResult(Token token) {
			super(token);
			value = 1;

			log.debug("%s: %d", token, value);
		}
	}

	public static class SentenceResult extends WordListResult implements Result.SentenceResult {

		public SentenceResult(Token token) {
			super(token);
			log.debug("%s: %d", token, value);
		}
	}

	public static class ParagraphResult extends WordListResult implements Result.ParagraphResult {

		public ParagraphResult(Token token) {
			super(token);
			log.debug("%s: %d", token, value);
		}
	}

	public static class CorpusResult extends WordListResult implements Result.CorpusResult {

		public CorpusResult(Token token) {
			super(token);
			log.debug("%s: %d", token, value);
		}
	}
}
