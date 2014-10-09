

Exact Matching
==============

Return all positions where a given pattern occurs in a given string.


Fundamental Preprocessing
-------------------------

The fundamental preprocessing necessary for linear (and sub-linear) time exact matching algorithms is to build a table 
for a string S that lists, for each index of S, the longest substring beginning at that index that matches a prefix of S.
 
Example

S = "aabcaabxaaz"

//table is zero-indexed
 
 Z[1] = 1  // aa
 Z[2] = 0
 Z[4] = 3  // aabc ... aabx
 Z[5] = 1  // aa ...... ab
 ...
 
 
In order to achieve linear time, the algorithm 

S = "aaabcaaabxaaz"