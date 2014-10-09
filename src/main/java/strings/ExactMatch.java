package strings;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExactMatch {

    String pattern;
    String text;
    final String nonAlphabetChar = "$"; //this must a char not found in the alphabet of the pattern or text to be searched

    public ExactMatch(String pattern, String text) {
        this.pattern = pattern;
        this.text = text;
    }

    //should this return a Set?
    public List<Integer> getMatchingPositions(){
        String s = pattern + nonAlphabetChar + text;
        FundamentalPreprocessor fp = new FundamentalPreprocessor(s);
        int[] zTable = fp.generateZTable();
        return IntStream.range(pattern.length(), s.length()).boxed()
                .filter(i -> zTable[i] == pattern.length())
                .map(i -> i - (pattern.length() + nonAlphabetChar.length()))
                .collect(Collectors.toList());
    }

}
