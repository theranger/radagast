# Valence Processor
Computes text valence using two separate stages:

* Comparing each word in text to existing valence word list.
* Obtaining per sentence probability by using naive bayes algorithm on a model pre-trained with emotional text corpus.

This processor determines valence in the following discrete scale:

| Name     | Definition                                |
|:---------|:------------------------------------------|
| NEUTRAL  | Contains no emotional information         |
| POSITIVE | Has positive meaning                      |
| NEGATIVE | Has negative meaning                      |
| MIXED    | Contains more than one valence from above |


## Word List Lookup
Each word is looked up from a predefined emotional dictionary, obtaining the integer value:

| Value | Definition                                      | Classification result |
|------:|:------------------------------------------------|:----------------------|
|     0 | Word is neutral or does not exist in dictionary | NEUTRAL               |
|     1 | Word is positive                                | POSITIVE              |
|    -1 | Word is negative                                | NEGATIVE              |
|    -8 | Word is extremely negative                      | EXTREME               |

### Aggregation
All tokens not explicitly stated otherwise are aggregated together by group-summarizing results of the sub-tokens. For example, having child tokens classified as:

```
	Child1 = NEUTRAL(Z1)
	Child2 = POSITIVE(P1)
	Child3 = NEUTRAL(Z2)
	Child4 = NEGATIVE(N1)
	Child5 = NEUTRAL(Z3)
	Child6 = POSITIVE(P2)
```
result of the current token would be calculated like:
```
	Token = { NEUTRAL(Z1 + Z2 + Z3), POSITIVE(P1 + P2), NEGATIVE(N1) }
```

where arguments Zx, Px and Nx of each valence function is a total number of words classified as being in that valence.

### Paragraph
As an exception from the above, paragraph level results are aggregated by summarizing all words in all sentences of the paragraph using a following rule set:

| Classified word count                   | Final classification of all words            |
|:----------------------------------------|:---------------------------------------------|
| extreme > 0                             | Negative                                     |
| positive > negative                     | Positive                                     |
| negative > positive                     | Negative                                     |
| positive = negative                     | Mixed                                        |
| positive = 0, negative = 0, extreme = 0 | Neutral                                      |

Above means that if any word in a paragraph was classified as extremely negative, all words in that paragraph are counted as negative etc.

### Corpus
Corpus results would first be aggregated from paragraph results using the method described above.
After that, further post-processing will be done using following rules.

Valence for a corpus consist of two elements: valence result and a quantifier. Quantifier can be one of `ONLY` or `MOSTLY`.

* If only one emotion is found, the corpus would be classified as `ONLY` that emotion.
* If two emotions were found and one contains more than a half words than the other, the corpus would be classified as `MOSTLY` that emotion.
* If none of the above rules apply, corpus will be classified as `MOSTLY` mixed emotion.
