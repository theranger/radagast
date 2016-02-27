package ee.risk.radagast.classifier;

import ee.risk.radagast.tokenizer.Token;

/**
 * Created by ranger on 27.02.16.
 */
public interface Classifier {
	void classify(Token token);
}
