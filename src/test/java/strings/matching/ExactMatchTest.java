package strings.matching;

import org.junit.Test;
import strings.matching.ExactMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ExactMatchTest {

    @Test
    public void testGetMatchingPositions(){
        String pattern = "aab";
        String text = "aabcaabxaaz";
        ExactMatch e = new ExactMatch(pattern, text);
        List<Integer> expected  = new ArrayList<>(Arrays.asList(0, 4));
        assertEquals(expected, e.getMatchingPositions());
    }

}