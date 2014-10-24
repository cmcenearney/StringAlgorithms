package strings.matching;

import org.junit.Test;

public class EditDistanceTest {


    @Test
    public void testLevenshteinDistanceEmptyStrings(){
        assert(EditDistance.levenshtein("","") == 0);
    }

    @Test
    public void testLevenshteinDistanceOneEmptyString(){
        assert(EditDistance.levenshtein("a","") == 1);
        assert(EditDistance.levenshtein("","abc") == 3);
    }

    @Test
    public void testLevenshteinDistanceStringsOfLengthOne(){
        assert(EditDistance.levenshtein("a","b") == 1);
    }

    @Test
    public void testLevenshteinDistanceSimpleCases(){
        assert(EditDistance.levenshtein("abc","bbc") == 1);
        assert(EditDistance.levenshtein("aac","bbc") == 2);
        assert(EditDistance.levenshtein("aac","bbb") == 3);
    }

    @Test
    public void testLevenshteinDistanceLessSimpleCases(){
        assert(EditDistance.levenshtein("abc","abcdef") == 3);
        assert(EditDistance.levenshtein("howdy","pard") == 4);
        assert(EditDistance.levenshtein("unction","junction") == 1);
    }

}