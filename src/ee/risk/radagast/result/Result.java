package ee.risk.radagast.result;

/**
 * Created by ranger on 27.02.16.
 */
public interface Result {
	void aggregate(Result result);
	void reduce(Result result);

	interface WordResult extends Result {}
	interface SentenceResult extends Result {}
	interface ParagraphResult extends Result {}
	interface CorpusResult extends Result {}
}
