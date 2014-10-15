package strings;

import org.junit.Test;
import util.Fasta;
import util.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class SuffixTreeNaiveTest {

    @Test
    public void testAllSuffixesOmitsTerminatingChar(){
        String s = "abcdefg$";
        HashSet<String> expected = new HashSet<String>(Arrays.asList("abcdefg$", "bcdefg$", "cdefg$", "defg$", "efg$","fg$", "g$"));
        assertEquals(expected, SuffixTreeNaive.allSuffixes(s));
    }

    @Test
    public void testTerminatingCharProgession(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("one");
        String expected = new String(Character.toChars(0x7b));
        assertEquals(expected, t.terminatingChars.get(t.terminatingChars.size() - 1));
        t.addString("two");
        expected = new String(Character.toChars(0x7c));
        assertEquals(expected, t.terminatingChars.get(t.terminatingChars.size() - 1));
    }

    @Test
    public void testAllNodesAreCommonToAllWithSingleInput(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTACA");
        List<TreeNode> lcs = t.getCommonSubStringNodes();
        t.getCommonSubStringNodes().stream()
                .forEach(n -> assertTrue(t.subTreeContainsAllInputs(n)));
    }

    @Test
    public void testNodeValueSimple(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GA");
        t.addString("AT");
        List<TreeNode> lcs = t.getCommonSubStringNodes();
        assert(lcs.size() == 1);
        assertEquals("A", t.nodeValue(lcs.get(0)));
    }

    @Test
    public void testNodeValueSimple2(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GAT");
        t.addString("ATT");
        Set<String> lcs = t.getCommonSubStringNodes().stream()
                .map(n -> t.nodeValue(n))
                .collect(Collectors.toSet());
        HashSet<String> expected = new HashSet<String>(Arrays.asList("T","AT"));
        assert(lcs.size() == 2);
        assertEquals(expected, lcs);
    }


    @Test
    public void testSmallCase2(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTACA");
        t.addString("TAGACCA");
        t.addString("ATACA");
        t.getCommonSubStringNodes();
        Set<String> lcs = t.getCommonSubStringNodes().stream()
                .map(n -> t.nodeValue(n))
                .collect(Collectors.toSet());
        HashSet<String> expected = new HashSet<String>(Arrays.asList("TA","AC","CA", "T", "C", "A"));
        assertEquals(expected, lcs);
    }

    @Test
    public void testSmallCase3(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTABA");
        t.addString("TAGABCA");
        t.addString("ATABA");
        t.getCommonSubStringNodes();
        Set<String> lcs = t.getCommonSubStringNodes().stream()
                .map(n -> t.nodeValue(n))
                .collect(Collectors.toSet());
        HashSet<String> expected = new HashSet<String>(Arrays.asList("A","AB","B", "T", "TA"));
        assertEquals(expected, lcs);
    }

    @Test
    public void testSmallCase4(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTDFDFDFABCA");
        t.addString("TAGABCA");
        t.addString("ATABCA");
        t.getCommonSubStringNodes();
        Set<String> lcs = t.getCommonSubStringNodes().stream()
                .map(n -> t.nodeValue(n))
                .collect(Collectors.toSet());
        HashSet<String> expected = new HashSet<String>(Arrays.asList("A", "BCA", "ABCA", "T", "CA"));
                // the 'raw' common substrings are different than the 'real' ones, which would be:
                //new HashSet<String>(Arrays.asList("A","B","C", "T", "AB","ABC","ABCA", "BCA", "BC"));
        assertEquals(expected, lcs);
    }

    @Test
    public void testWithRosalindData() throws IOException {
        String expected = "ACTGCGGTCTAAGGCGACGCAATGAGGAGGTAGAT";
        String raw = new String(Files.readAllBytes(Paths.get("src/test/resources/rosalind_lcsm.txt")));
        List<Fasta> inputs = Utils.parseRawFastas(raw);
        assert(inputs.size() == 100);
        SuffixTreeNaive t = new SuffixTreeNaive();
        for(Fasta f : inputs){
            t.addString(f.getSeq());
        }
        assertEquals(expected, t.getLongestCommonSubStrings().get(0));
    }

//    @Test
//    public void testUnicodeLiterals(){
//        System.out.println("\u00ad");
//        System.out.println(Integer.toHexString(123));
//        System.out.println(0x7b);
//        System.out.println(0xFFFF);
//        System.out.println(new String(Character.toChars(0x10FFFF)));
//        String s = "abcdefg";
//        System.out.print(  s.substring(0,s.length()));
//        //IntStream.range(0x7b, 0xff).forEachOrdered(i -> System.out.println( "\\u00" + Integer.toHexString(i) ));
//    }


}