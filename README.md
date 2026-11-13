# Aho-Corasick vs KMP: Performance Comparison

## 1. Introduction

In this assignment, I implemented Aho-Corasick in Java and compared it with KMP. The goal was to find multiple patterns in a text and compare the performance of both algorithms.

## 2. Implementation

The project contains three Java classes:

- AhoCorasick.java - implementation of the Aho-Corasick algorithm.
- KMP.java - implementation of KMP.
- Main.java - testing and measuring execution time.

Aho-Corasick stores all patterns in a trie. Failure links are built using BFS and help continue searching when a character does not match. Output links help find multiple patterns ending at the same position.

KMP uses an LPS array and searches for each pattern separately.

## 3. Complexity Analysis

| Algorithm | Time | Space |
|---|---|---|
| Aho-Corasick | O(S + n + z) expected | O(S + z) |
| KMP | O(S + k * n + z) | O(L + z) |

Where n is the text length, k is the number of patterns, S is the total length of patterns, L is the longest pattern length, and z is the number of matches.

Aho-Corasick searches the text once for all patterns, while KMP searches the text again for every pattern.

## 4. Testing

I tested both algorithms with two examples:

- Text: ushers, patterns: he, she, his, hers.
- Text: aaa, patterns: a, aa.

Both tests passed and the algorithms returned the correct results, including overlapping matches.

For performance testing, I used 10, 100 and 1000 patterns with text lengths of 1000, 10000 and 100000 characters.

Each experiment was repeated 5 times using System.nanoTime(). The average time includes preprocessing and searching.

## 5. Results

| Patterns | Text length | Aho-Corasick (ns) | KMP (ns) |
|---|---|---|---|
| 10 | 1000 | 335450 | 992658 |
| 10 | 10000 | 1266500 | 3347091 |
| 10 | 100000 | 3596658 | 4003975 |
| 100 | 1000 | 499550 | 501900 |
| 100 | 10000 | 690258 | 3993124 |
| 100 | 100000 | 3067791 | 36452358 |
| 1000 | 1000 | 2826566 | 4064033 |
| 1000 | 10000 | 3868433 | 63424699 |
| 1000 | 100000 | 3866333 | 250328166 |

All results are saved in results.csv.

## 6. Conclusion

From my results, Aho-Corasick was faster than KMP in all test cases.

With a small number of patterns, the difference was not very big. But with 1000 patterns and 100000 characters, Aho-Corasick was about 65 times faster.

Aho-Corasick is better for searching many patterns because it only goes through the text once. However, it needs more memory for the trie and links.

KMP is simpler and uses less extra memory, which makes it a good choice for searching one pattern.

## 7. How to Run

Open the project in IntelliJ IDEA and run Main.java. The program runs the tests and saves the results in results.csv.