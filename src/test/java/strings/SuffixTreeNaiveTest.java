package strings;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SuffixTreeNaiveTest {

    @Test
    public void testAllSuffixes(){
        String s = "abcdefg";
        HashSet<String> expected = new HashSet<>(Arrays.asList("abcdefg", "bcdefg", "cdefg", "defg", "efg","fg", "g"));
        assertEquals(expected, SuffixTreeNaive.allSuffixes(s));
    }

    @Test
    public void testing(){
        String s = "abcdefg";
        String t = "abc";
        System.out.println(s.substring(t.length()));
        System.out.println(SuffixTreeNaive.getLastMatchingIndex("onesies", "onerous"));
        SuffixTreeNaive tr = new SuffixTreeNaive();
        tr.addNode(s);
        tr.addNode("abde");
        tr.addNode(t);
        System.out.println(tr.findNode(t));
    }

    @Test
    public void testUnicodeLiterals(){
        System.out.println("\u00ad");
        System.out.println(Integer.toHexString(123));
        System.out.println(0x7b);
        System.out.println(0x10FFFF);
        System.out.println(new String(Character.toChars(0x10FFFF)));
        //IntStream.range(0x7b, 0xff).forEachOrdered(i -> System.out.println( "\\u00" + Integer.toHexString(i) ));
    }


}