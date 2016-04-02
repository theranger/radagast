# Results handling
In order to compute final results of the text entry, two phases are used:

1. **Aggregation**, to compute certain processing results across all the tokens.
2. **Reduction**, to compute final results across all the aggregated results.

## Aggregation
Every processor must have a capability to classify each token (corpus, paragraph, sentence, word) separately and
produce intermediate results for them accordingly. It is usual for a processor to work at a specific token level
and classify those tokens using internal algorithms. When moving recursively to upper levels (from words to sentences,
from sentences to paragraphs etc.) previously computed results from sub-tokens might be added together to form a
result for the higher-level token.

For example, valence processor will classify each word as being positive, negative, neutral or extremely negative.
When moving up to sentence level, previously calculated valences can be then added together to classify the whole
sentence. Again, moving up to paragraph level, previously computed sentence valences can again be added together to
compute a paragraph valence.

**Aggregation merges only the results of the same type.** Each token will have results from different processors and
therefore those results will be from different type. Current processor for current token will walk through the list
of results for each sub-token and compute only those that match with the current result being processed.


## Reduction
Every processor creates results from different type either by classifying or merging previously computed results together.
Either way, the final computed result would be the same type as the child ones. In order to provide meaningful
summarized value above all the results from all processors, result reduction must be performed.

**Reduction merges together different type of results,** but operates only on one token level. It will never look
into sub-tokens nor their results. Although reduction can be done on different tokens as well, most meaningful
would be run this operation at `Entry` or `Corpus` level. This process calls the `reduce()` operation for each
result from the result list of the current token. Every result must implement this functionality and should add their
internal results to the final `ReductionResult` object passed as an argument to the reduction function.

It is up to the result object to decide how the final reduced result should be mangled.
