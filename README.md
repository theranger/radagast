About Radagast
==============

Radagast is a text classification framework for analyzing user submitted content. It is a modular system for processing text entries, decomposing them into tokens and combining results with various metadata associated with original content.

Radagast contains a set of predefined modules that operate at different tokenization levels (e.g. paragraphs, sentences, words), analyze tokens and compute results. Result can either be in a numerical form or suitable quantifiable values.

Radagast is a framework so it can be extended by adding additional modules. All processing and result calculation interfaces are generalized and can be implemented at any tokenization level needed.

Project is written in Java. The language was chosen because of strong typing and fully object oriented paradigm. Some parts of the original framework include components from third-party projects that are written in C or C++. Capabilities of including such artifacts using a Java Native Interface (JNI) was one of the key factors for choosing Java.

Original goal of this project was to provide text analyzing and classification for content written in Estonian language. Although many design decisions and components are influenced by peculiarities of Estonian, this framework can be extended to work with other languages as well.
