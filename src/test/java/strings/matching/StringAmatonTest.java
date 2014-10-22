package strings.matching;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringAmatonTest {

    @Test
    public void basicTest(){
        String pattern = "abc";
        String text = "hijklabcmnop";
        StringAmaton s = new StringAmaton(pattern);
        for (char c : text.toCharArray()){
            s.nextChar(c);
        }
        assertEquals(true, s.getMatched());
    }

}