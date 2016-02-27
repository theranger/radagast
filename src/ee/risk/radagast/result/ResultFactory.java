package ee.risk.radagast.result;

import ee.risk.radagast.tokenizer.Token;

/**
 * Created by ranger on 27.02.16.
 */
public interface ResultFactory {
	Result.WordResult createWordResult(Token token);
	Result.SentenceResult createSentenceResult(Token token);
	Result.ParagraphResult createParagraphResult(Token token);
	Result.CorpusResult createCorpusResult(Token token);
}
