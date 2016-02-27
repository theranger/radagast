package ee.risk.radagast.tokenizer;

import ee.risk.radagast.result.Result;
import ee.risk.radagast.result.ResultFactory;

import java.util.ArrayList;

/**
 * Created by ranger on 27.02.16.
 */
public abstract class Token {

	protected ArrayList<Token> tokens = new ArrayList<>();
	protected ArrayList<Result> results = new ArrayList<>();
	protected String value;

	public Token(String value) {
		this.value = value;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void addResult(Result result) {
		results.add(result);
	}

	public abstract Result createResult(ResultFactory resultFactory);

	@Override
	public String toString() {
		if(value != null) return value;
		return "<token>";
	}
}
