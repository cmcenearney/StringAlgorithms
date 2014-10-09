package strings;

import org.junit.Test;

import java.util.List;

public class ExactMatchTest {

    @Test
    public void testZ(){
        String pattern = "aab";
        String text = "aabcaabxaaz";
        ExactMatch e = new ExactMatch(pattern, text);
        List<Integer> m  = e.getMatchingPositions();
        System.out.println(m);
    }

}