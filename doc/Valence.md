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

Results will be calculated at the paragraph level by summarizing all words in that paragraph using following rule set:

| Classified word count                   | Final classification of all words            |
|:----------------------------------------|:---------------------------------------------|
| extreme > 0                             | Negative                                     |
| positive > negative                     | Positive                                     |
| negative > positive                     | Negative                                     |
| positive = negative                     | Mixed                                        |
| positive = 0, negative = 0, extreme = 0 | Neutral                                      |

Above means that if any word in a paragraph was classified as extremely negative, all words in that paragraph are counted as negative etc.
